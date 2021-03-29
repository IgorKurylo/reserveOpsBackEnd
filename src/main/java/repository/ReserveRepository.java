package repository;

import exceptions.ReserveException;
import models.AvailableTime;
import models.Reserve;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IReserveRepository;
import utils.Converters;

import javax.inject.Inject;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ReserveRepository implements IReserveRepository {

    private final String _RESERVE_BY_ID_QUERY = "SELECT * FROM reserve WHERE id = %d";
    private final String _RESERVE_DELETE = "DELETE FROM reserve WHERE Id = ?";
    private final String _RESERVE_UPDATE_METADATA = "UPDATE reserve SET reservedate=?,SET reservetime=? guest=? WHERE id=?";
    private final String _RESERVE_UPDATE_STATUS = "UPDATE reserve SET status=? WHERE Id=?";

    private final String _RESERVE_CREATE = "INSERT INTO reserve " +
            "(usrid, restid, tblid, reservedate, reservetime, guests, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String _AVAILABLE_TIME = "SELECT to_char(reservetime,'HH24:MI') as reservetime, worktimestart, worktimeend " +
            "FROM (SELECT reservetime, r.restid, worktimeend, worktimestart " +
            "FROM reserve " +
            "INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate = '%s') as O " +
            "WHERE restid = %d";

    @Inject
    IDatabaseConnection _connection;

    @Override
    public int create(Reserve reserve) {
        Optional<Connection> connection = this._connection.open();
        int orderId = 0;
        if (connection.isPresent()) {
            Connection conn = connection.get();
            Statement statement = null;
//            try {
//
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            } finally {
//                _connection.close();
//            }
        }
        return orderId;
    }

    @Override
    public Reserve update(Reserve reserve) {
        Optional<Connection> connection = this._connection.open();
        connection.ifPresent(conn -> {

        });
        return null;
    }

    @Override
    public void delete(int id) throws ReserveException {
        Optional<Connection> connection = this._connection.open();
        if (connection.isPresent()) {
            try {
                PreparedStatement preparedStatement = connection.get().prepareStatement(_RESERVE_DELETE);
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows <= 0) {
                    throw new ReserveException(String.format("Can't delete order id %d", id));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        }
    }

    @Override
    public Reserve getReserve(int id) {
        Optional<Connection> connection = this._connection.open();
        String _orderQuery = String.format(_RESERVE_BY_ID_QUERY, id);
        Reserve order = new Reserve();
        connection.ifPresent(conn -> {
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(_orderQuery);
                while (resultSet.next()) {
                    order.setId(id);
                    order.setGuest(resultSet.getInt("Guests"));
                    //order.setOrderStatus(resultSet.getString("ordSts"));
                    order.setTableId(resultSet.getInt("TblId"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        });
        return order;
    }

    @Override
    public List<Reserve> getReserves(int restaurantId, String date, int userId) {
        return null;
    }


    @Override
    public List<AvailableTime> availableTimes(int restaurantId, String date) {

        List<AvailableTime> list = new ArrayList<>();
        List<String> notAvailableTimes = new ArrayList<>();
        Optional<Connection> connection = this._connection.open();
        AtomicReference<String> startTime = new AtomicReference<>();
        AtomicReference<String> endTime = new AtomicReference<>();
        connection.ifPresent(conn -> {

            Statement statement = null;
            try {
                String query = String.format(_AVAILABLE_TIME, date, restaurantId);
                statement = conn.createStatement();
                ResultSet result = statement.executeQuery(query);

                while (result.next()) {
                    startTime.set(result.getString("worktimestart"));
                    endTime.set(result.getString("worktimeend"));
                    String orderTime = result.getString("reservetime");
                    notAvailableTimes.add(orderTime);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });

        return buildAvailableTimes(startTime.get(), endTime.get(), notAvailableTimes);
    }

    private List<AvailableTime> buildAvailableTimes(String start, String end, List<String> notAvailableTimes) {
        List<AvailableTime> list = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {

            if (notAvailableTimes.size() > 0) {
                java.util.Date startTime = Converters.convertTimeFromString(start);
                java.util.Date endTime = Converters.convertTimeFromString(end);
                c1.setTime(startTime);
                c2.setTime(endTime);
                int startHour = c1.get(Calendar.HOUR_OF_DAY);
                int endHour = c2.get(Calendar.HOUR_OF_DAY) == 0 ? 24 : c2.get(Calendar.HOUR_OF_DAY);
                while (startHour <= endHour) {
                    insertToAvailableTimeList(list, startHour);
                    startHour++;
                }
                for (AvailableTime availableTime : list) {
                    if (notAvailableTimes.contains(availableTime.getTime())) {
                        availableTime.setAvailable(false);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void insertToAvailableTimeList(List<AvailableTime> list, int startHour) {
        list.add(new AvailableTime(startHour < 10 ?
                String.format(Locale.getDefault(), "%d%d:00", 0, startHour)
                : String.format(Locale.getDefault(), "%d:00", startHour)
                , true));
    }


}