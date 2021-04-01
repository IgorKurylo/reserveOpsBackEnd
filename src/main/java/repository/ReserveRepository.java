package repository;

import exceptions.ReserveException;
import models.AvailableTime;
import models.Reserve;
import models.ReserveStatus;
import models.RestaurantTables;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IReserveRepository;
import utils.Const;
import utils.Converters;
import utils.Logs;

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
            "(usrid, restid, tblid, reservedate, reservetime, guests, status,comments) VALUES (?, ?, ?, ?, ?, ?, ?,?)";

    private final String _AVAILABLE_TIME = "SELECT to_char(reservetime,'HH24:MI') as reservetime, worktimestart, worktimeend " +
            "FROM (SELECT reservetime, r.restid, worktimeend, worktimestart " +
            "FROM reserve " +
            "INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate = '%s') as O " +
            "WHERE restid = %d";
    private final String _GET_RESTAURANT_TABLES = "SELECT t.id, t.seats FROM rest_table " +
            "INNER JOIN tables t on t.id = rest_table.tblid " +
            "WHERE restid=%d order by t.seats";
    private final String _CHECK_TABLE_AVAILABLE = "SELECT rest_table.seats,rest_table.tblid" +
            "FROM rest_table" +
            "RIGHT JOIN  reserve r on rest_table.restid = r.restid" +
            "WHERE rest_table.restid=%d and rest_table.tblid <> r.tblid" +
            "and rest_table.seats between %d and %d guests=%d and reservedate='%s' and reservetime<>'%s'";

    @Inject
    IDatabaseConnection _connection;
    private Logs logs;

    public ReserveRepository() {
        logs = Logs.getInstance().init(ReserveRepository.class.getName());
    }

    @Override
    public Reserve create(Reserve reserve, int userId) {

        Optional<Connection> connection = this._connection.open();
        logs.infoLog(reserve.toString());
        if (connection.isPresent()) {
            Connection conn = connection.get();
            try {
                List<RestaurantTables> tables = getTables(reserve, conn);
                if (tables.size() > 0) {
                    RestaurantTables t = BusinessLogic.findTable(reserve.getGuest(), tables);
                    if (t != null) { // If is a null? what doing
                        reserve.setTableId(t.getTableId());
                        PreparedStatement statement = prepareReserveCreation(reserve, userId, conn);
                        if (statement.executeUpdate() > 0) {
                            ResultSet generatedKeys = statement.getGeneratedKeys();
                            if (generatedKeys.next()) {
                                reserve.setId(generatedKeys.getInt(1));
                            }
                        }
                    }
                } else {

                    //TODO: find other suggestion for user and send
                }
            } catch (SQLException ex) {
                logs.errorLog(ex.getMessage());
            }
            finally {
                _connection.close();
            }
        }
        logs.infoLog(reserve.toString());
        return reserve;
    }

    private PreparedStatement prepareReserveCreation(Reserve reserve, int userid, Connection conn) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(_RESERVE_CREATE, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, userid);
        statement.setInt(2, reserve.getRestaurant().getId());
        statement.setInt(3, reserve.getTableId());
        statement.setDate(4, Converters.toSQLDateType(reserve.getDate()));
        statement.setTime(5, Converters.toSQLTimeType(reserve.getTime()));
        statement.setInt(6, reserve.getGuest());
        statement.setObject(7, ReserveStatus.Waiting.name(), Types.OTHER);
        statement.setString(8, reserve.getComment());
        return statement;
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
            finally {
                _connection.close();
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

    private List<RestaurantTables> getTables(Reserve reserve, Connection conn) throws SQLException {
        List<RestaurantTables> tables = new ArrayList<>();
        Statement selectStatement;
        selectStatement = conn.createStatement();
        String tablesQuery = String.format(_CHECK_TABLE_AVAILABLE, reserve.getRestaurant().getId(), reserve.getGuest(),
                reserve.getGuest() * Const.MULTIPLY_NUMBER_OF_MAX_SEATS, reserve.getGuest(), reserve.getDate(), reserve.getTime());
        ResultSet resultSet = selectStatement.executeQuery(tablesQuery);
        while (resultSet.next()) {
            tables.add(new RestaurantTables(resultSet.getInt("tblid"), resultSet.getInt("seats")));
        }
        return tables;
    }

    private boolean IsTableAvailable(int tableId, int guestRequestNumber, String date, String time, Connection conn) throws SQLException {
        Statement selectStatement;
        boolean isAvailable = false;
        selectStatement = conn.createStatement();
        String tablesQuery = String.format(_CHECK_TABLE_AVAILABLE, tableId, guestRequestNumber, date, time);
        ResultSet resultSet = selectStatement.executeQuery(tablesQuery);
        isAvailable = !resultSet.next();
        return isAvailable;
    }


}