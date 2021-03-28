package repository;

import exceptions.OrderException;
import models.AvailableTime;
import models.Order;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IOrderRepository;
import utils.Converters;

import javax.inject.Inject;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class OrderRepository implements IOrderRepository {

    private final String _ORDER_BY_ID_QUERY = "SELECT * FROM postgres.\"Order\" WHERE Id = %d";
    private final String _ORDER_DELETE = "DELETE FROM postgres.\"Order\" WHERE Id = ?";
    private final String _ORDER_UPDATE_METADATA = "UPDATE postgres.\"Order\" SET orddate=?,SET ordtime=? guest=? WHERE id=?";
    private final String _ORDER_UPDATE_STATUS = "UPDATE postgres.\"Order\" SET ordsts=? WHERE id=?";

    private final String _ORDER_CREATE = "INSERT INTO postgres.\"Order\" " +
            "(ordid, usrid, restid, tblid, orddate, ordtime, guests, ordsts) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private final String _ORDER_NOT_AVAILABLE = "SELECT OrdId FROM postgres.\"Order\" WHERE OrdDate=%s and OrdTime=%s";

    private final String _AVAILABLE_TIME = "SELECT to_char(ordtime,'HH24:MI') as ordtime, worktimestart, worktimeend " +
            "FROM (SELECT ordtime, r.restid, worktimeend, worktimestart " +
            "FROM \"Order\" " +
            "INNER JOIN restaurant r on r.restid = \"Order\".restid " +
            "WHERE orddate = '%s') as O " +
            "WHERE restid = %d";

    @Inject
    IDatabaseConnection _connection;

    @Override
    public int create(Order order) {
        Optional<Connection> connection = this._connection.open();
        int orderId = 0;
        if (connection.isPresent()) {
            Connection conn = connection.get();
            Statement statement = null;
            try {

                statement = conn.createStatement();
                String _query = String.format(_ORDER_NOT_AVAILABLE, order.getDate(), order.getTime());
                ResultSet resultSet = statement.executeQuery(_query);
                while (resultSet.next()) {
                    orderId = resultSet.getInt("OrdId");
                }
                if (orderId != 0) {

                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        }
        return orderId;
    }

    @Override
    public Order update(Order order) {
        Optional<Connection> connection = this._connection.open();
        Order o = new Order();
        connection.ifPresent(conn -> {

        });
        return null;
    }

    @Override
    public void delete(int id) throws OrderException {
        Optional<Connection> connection = this._connection.open();
        if (connection.isPresent()) {
            try {
                PreparedStatement preparedStatement = connection.get().prepareStatement(_ORDER_DELETE);
                preparedStatement.setInt(1, id);
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows <= 0) {
                    throw new OrderException(String.format("Can't delete order id %d", id));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        }
    }

    @Override
    public Order getOrder(int id) {
        Optional<Connection> connection = this._connection.open();
        String _orderQuery = String.format(_ORDER_BY_ID_QUERY, id);
        Order order = new Order();
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
    public List<Order> getOrders() {
        return new ArrayList<>();
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
                    String orderTime = result.getString("ordtime");
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
            java.util.Date startTime = Converters.convertTimeFromString(start);
            java.util.Date endTime = Converters.convertTimeFromString(end);
            c1.setTime(startTime);
            c2.setTime(endTime);
            int startHour = c1.get(Calendar.HOUR_OF_DAY);
            int endHour = c2.get(Calendar.HOUR_OF_DAY) == 0 ? 24 : c2.get(Calendar.HOUR_OF_DAY);
            if (notAvailableTimes.size() > 0) {
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