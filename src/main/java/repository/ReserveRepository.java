package repository;

import exceptions.CreateReserveException;
import models.*;
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
    private final String _CHECK_TABLE_AVAILABLE = "SELECT rest_table.seats,rest_table.tblid " +
            "FROM rest_table " +
            "RIGHT JOIN reserve r on rest_table.restid = r.restid " +
            "WHERE rest_table.restid=%d " +
            "and rest_table.seats>=%d and rest_table.seats<=%d and reservedate<>'%s' and reservetime<>'%s'";

    private final String _RESERVES_LIST =
            "SELECT restname,imageurl,to_char(reservetime,'HH24:MM') as time," +
                    "reservedate as date,guests,status " +
                    "FROM reserve " +
                    "INNER JOIN restaurant r on r.restid = reserve.restid " +
                    "WHERE usrid=%d";
    private final String _GET_TABLES_BY_REST_ID = "SELECT tblid,seats FROM rest_table WHERE restid=%d and rest_table.seats>=%d and rest_table.seats<=%d";

    @Inject
    IDatabaseConnection _connection;
    private Logs logs;

    public ReserveRepository() {
        logs = Logs.getInstance().init(ReserveRepository.class.getName());
    }

    @Override
    public Reserve create(Reserve reserve, int userId) throws CreateReserveException {

        Optional<Connection> connection = this._connection.open();
        logs.infoLog(reserve.toString());
        List<RestaurantTables> tables = new ArrayList<>();
        if (connection.isPresent()) {
            Connection conn = connection.get();
            try {
                tables = availableTables(reserve, conn);
                if (tables.size() > 0) {
                    insertReserve(reserve, tables, userId, conn);
                } else {
                    tables = getTables(reserve.getRestaurant().getId(), reserve.getGuest(), conn);
                    if (tables.size() > 0) {
                        insertReserve(reserve, tables, userId, conn);
                    } else {
                        throw new CreateReserveException(
                                "There not available tables");
                    }
                }
            } catch (SQLException ex) {
                logs.errorLog(ex.getMessage());
                throw new CreateReserveException("Error on creation");
            } finally {
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

    private void insertReserve(Reserve reserve, List<RestaurantTables> tables, int userId, Connection connection) throws SQLException {
        RestaurantTables t = BusinessLogic.findTable(tables);
        if (t != null) {
            reserve.setTableId(t.getTableId());
            PreparedStatement statement = prepareReserveCreation(reserve, userId, connection);
            if (statement.executeUpdate() > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    reserve.setId(generatedKeys.getInt(1));
                }
            }
        }
    }


    @Override
    public Reserve update(Reserve reserve) {
        Optional<Connection> connection = this._connection.open();
        connection.ifPresent(conn -> {

        });
        return null;
    }

    @Override
    public void delete(int id) throws CreateReserveException {
        Optional<Connection> connection = this._connection.open();
        if (connection.isPresent()) {
            try {
                PreparedStatement preparedStatement = connection.get().prepareStatement(_RESERVE_DELETE);
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows <= 0) {
                    throw new CreateReserveException(String.format("Can't delete order id %d", id));
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
    public List<Reserve> getReserves(String date, int userId) {
        List<Reserve> reserves = new ArrayList<>();
        Optional<Connection> connection = _connection.open();
        connection.ifPresent(conn -> {
            String query = String.format(_RESERVES_LIST, userId); // build query if date is set or not
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    reserves.add(new Reserve(resultSet.getString("date"),
                            resultSet.getString("time"), new Restaurant(
                            0, resultSet.getString("restname"),
                            "", "", resultSet.getString("imageurl"),
                            null, "", "", "", ""),
                            resultSet.getInt("guests"), 0,
                            ReserveStatus.valueOf(resultSet.getString("status")),
                            ""
                    ));
                }

            } catch (SQLException ex) {
                logs.errorLog(ex.toString());
            }
        });
        return reserves;
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
            } finally {
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

    private List<RestaurantTables> availableTables(Reserve reserve, Connection conn) throws SQLException {
        List<RestaurantTables> tables = new ArrayList<>();
        Statement selectStatement;
        selectStatement = conn.createStatement();
        String tablesQuery = String.format(_CHECK_TABLE_AVAILABLE, reserve.getRestaurant().getId(), reserve.getGuest(),
                reserve.getGuest() * Const.MULTIPLY_NUMBER_OF_MAX_SEATS, reserve.getDate(), reserve.getTime());
        ResultSet resultSet = selectStatement.executeQuery(tablesQuery);
        while (resultSet.next()) {
            tables.add(new RestaurantTables(resultSet.getInt("seats"), resultSet.getInt("tblid")));
        }
        return tables;
    }


    private List<RestaurantTables> getTables(int restId, int guests, Connection conn) throws SQLException {
        List<RestaurantTables> tables = new ArrayList<>();
        Statement selectStatement;
        selectStatement = conn.createStatement();
        String query = String.format(_GET_TABLES_BY_REST_ID, restId, guests, guests * Const.MULTIPLY_NUMBER_OF_MAX_SEATS);
        ResultSet resultSet = selectStatement.executeQuery(query);
        while (resultSet.next()) {
            tables.add(new RestaurantTables(resultSet.getInt("seats"), resultSet.getInt("tblid")));
        }
        return tables;
    }
}