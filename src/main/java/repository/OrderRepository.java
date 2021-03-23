package repository;

import exceptions.OrderException;
import models.Order;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IOrderRepository;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements IOrderRepository {

    private final String _ORDER_BY_ID_QUERY = "SELECT * FROM reserveops.\"Order\" WHERE Id = %d";
    private final String _ORDER_DELETE = "DELETE FROM reserveops.\"Order\" WHERE Id = ?";
    private final String _ORDER_UPDATE_METADATA = "UPDATE reserveops.\"Order\" SET orddate=?,SET ordtime=? guest=? WHERE id=?";
    private final String _ORDER_UPDATE_STATUS = "UPDATE reserveops.\"Order\" SET ordsts=? WHERE id=?";

    private final String _ORDER_CREATE = "INSERT INTO reserveops.\"Order\" " +
            "(ordid, usrid, restid, tblid, orddate, ordtime, guests, ordsts) VALUES (?, ?, ?, ?, ?, ?, ?)";

    @Inject
    IDatabaseConnection _connection;

    @Override
    public int create(Order order) {
        Optional<Connection> connection = this._connection.open();
        int orderId = 0;
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
}