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

    private final String _ORDER_BY_ID_QUERY = "SELECT * FROM Id=%d";
    private final String _ORDER_DELETE = "DELETE FROM Order WHERE Id = ?";
    private final String _ORDER_UPDATE = "";
    private final String _ORDER_CREATE = "";

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
        connection.ifPresent(conn -> {
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(_orderQuery);
                //TODO: get orders
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        });
        return null;
    }

    @Override
    public List<Order> getOrders() {
        return new ArrayList<>();
    }
}