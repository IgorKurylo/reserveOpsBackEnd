package repository;

import models.ReservationWeek;
import models.Reserve;
import models.Restaurant;
import models.requests.NotUpcomingReserve;
import models.response.StatisticResponse;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IStatisticRepository;
import utils.Const;
import utils.Converters;
import utils.Logs;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticRepository implements IStatisticRepository {

    @Inject
    IDatabaseConnection _connection;
    private final String _UPCOMING_QUERY =
            "SELECT reservedate as date, to_char(reservetime,'HH24:MM') as time, guests, restname " +
            "FROM reserve " +
            "INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate > CURRENT_DATE " +
            "and usrid = %d order by reservedate asc LIMIT 1 ";
    private final String _RESERVATION_QUERY = "SELECT count(reserve.id) as reservationCount FROM reserve WHERE usrid = %d";
    private final String _LAST_VISIT_QUERY = "SELECT  restname,reservedate " +
            "FROM reserve INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate < CURRENT_DATE " +
            "and usrid = %d order by reservedate desc LIMIT 1";

    private final String _RESERVATION_COUNT_BY_REST_ID_QUERY = "SELECT count(reserve.id) as reservationCount FROM reserve WHERE restid=%d and reservedate=CURRENT_DATE";
    private final String _PENDING_RESERVATION_REST_ID = "SELECT count(reserve.id) as pendingCount FROM reserve WHERE restid=%d and reservedate=CURRENT_DATE and status='Waiting'";
    private final String _WEEK_RESERVATION_STATISTICS =
            "SELECT count(reserve.id) as reservations,reservedate as date,worktimeend as timeend,worktimestart as timestart " +
                    "FROM reserve inner join restaurant r on r.restid = reserve.restid " +
                    "WHERE r.restid=%d and reservedate between current_date - extract(dow from current_date) * '1 day'::interval " +
                    "and current_date + (6 - extract(dow from current_date)) * '1 day'::interval " +
                    "GROUP BY reservedate,r.restid";

    private final Logs logs;

    public StatisticRepository() {
        logs = Logs.getInstance().init(ReserveRepository.class.getName());
    }

    @Override
    public Reserve upComing(int userId) throws NotUpcomingReserve {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_UPCOMING_QUERY, userId);
        Reserve reserve = new Reserve();
        connection.ifPresent(conn -> {
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    reserve.setDate(resultSet.getString("date"));
                    reserve.setTime(resultSet.getString("time"));
                    reserve.setGuest(resultSet.getInt("guests"));
                    reserve.setRestaurant(new Restaurant(resultSet.getString("restname")));
                } else {
                    throw new NotUpcomingReserve("No upcoming reservation");
                }
            } catch (SQLException | NotUpcomingReserve ex) {
                logs.errorLog(ex.getMessage());
            } finally {
                _connection.close();
            }
        });
        return reserve;
    }

    @Override
    public int reservations(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_RESERVATION_QUERY, userId);
        int reservationNumber = 0;
        if (connection.isPresent()) {
            Connection c = connection.get();
            Statement statement = null;
            try {
                statement = c.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    reservationNumber = resultSet.getInt("reservationCount");
                }
            } catch (SQLException ex) {
                logs.errorLog(ex.getMessage());
            } finally {
                _connection.close();
            }
        }
        return reservationNumber;
    }

    @Override
    public Restaurant lastVisit(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_LAST_VISIT_QUERY, userId);
        Restaurant restaurant = new Restaurant();
        connection.ifPresent(conn -> {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    restaurant.setRestaurantName(resultSet.getString("restname"));

                }

            } catch (SQLException ex) {
                logs.errorLog(ex.getMessage());
            } finally {
                _connection.close();
            }

        });
        return restaurant;
    }

    @Override
    public List<ReservationWeek> reservationsStatistic(int restaurantId) {
        List<ReservationWeek> list = new ArrayList<>(Collections.nCopies(Const.DAY_OF_WEEK, new ReservationWeek()));
        List<ReservationWeek> originList = new ArrayList<>();
        Optional<Connection> connection = this._connection.open();
        connection.ifPresent(conn -> {
            String query = String.format(_WEEK_RESERVATION_STATISTICS, restaurantId);
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                initList(list);
                while (resultSet.next()) {
                    int reservationCnt = resultSet.getInt("reservations");
                    String date = resultSet.getString("date");
                    String start = resultSet.getString("timestart");
                    String end = resultSet.getString("timeend");
                    Date reserveDate = Converters.convertDateFromString(date);
                    int index = findReservationIndex(list, reserveDate);
                    if (index != -1) {
                        list.set(index, buildReservationWeekObj(reserveDate, start, end, reservationCnt));
                    }
                }

            } catch (SQLException | ParseException ex) {
                logs.errorLog(ex.toString());
            }
        });
        originList = list.stream().skip(1).collect(Collectors.toList());
        return originList;
    }

    private void initList(List<ReservationWeek> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).getDay() == null) {
                list.set(i, new ReservationWeek(DayOfWeek.of(i).getDisplayName(TextStyle.SHORT, Locale.getDefault()), 0, 0));
            }
        }
    }

    private int findReservationIndex(List<ReservationWeek> list, Date reserveDate) {
        int index = -1;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getDay() != null) {
                if (list.get(i).getDay().equals(Converters.getDayOfWeekName(reserveDate))) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    private ReservationWeek buildReservationWeekObj(Date reserveDate, String start, String end, int reservationCnt) {
        ReservationWeek reservationWeek = new ReservationWeek();
        try {
            Date startTime = Converters.convertTimeFromString(start);
            Date endTime = Converters.convertTimeFromString(end);
            reservationWeek.setDay(Converters.getDayOfWeekName(reserveDate));
            reservationWeek.setReservations(reservationCnt);
            long diffTime = Math.abs(startTime.getTime() - endTime.getTime());
            int hour = (int) diffTime / (60 * 60 * 1000) % 24;
            reservationWeek.setReservationsInPercent((double) reservationCnt / hour);
        } catch (ParseException e) {
            logs.errorLog(e.toString());
        }
        return reservationWeek;
    }


    @Override
    public int todayReservation(int restaurantId) {
        Optional<Connection> connection = this._connection.open();
        int todayReservation = -1;

        String query = String.format(_RESERVATION_COUNT_BY_REST_ID_QUERY, restaurantId);
        if (connection.isPresent()) {
            Connection conn = connection.get();
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    todayReservation = resultSet.getInt("reservationCount");
                }

            } catch (SQLException ex) {
                logs.errorLog(ex.toString());
            }
        }
        return todayReservation;
    }

    @Override
    public int pendingReservation(int restaurantId) {
        Optional<Connection> connection = this._connection.open();
        int pendingReservation = -1;

        String query = String.format(_PENDING_RESERVATION_REST_ID, restaurantId);
        if (connection.isPresent()) {
            Connection conn = connection.get();
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    pendingReservation = resultSet.getInt("pendingCount");
                }

            } catch (SQLException ex) {
                logs.errorLog(ex.toString());
            } finally {
                _connection.close();
            }
        }
        return pendingReservation;
    }
}
