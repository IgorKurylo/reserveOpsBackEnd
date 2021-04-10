package repository;

import models.Restaurant;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IUserRepository;
import utils.Logs;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserRepository implements IUserRepository {

    @Inject
    IDatabaseConnection _connection;

    private Logs logs;

    private final String _ADMIN_RESTAURANT_INFO = "SELECT restname,imageurl,restid FROM restaurant WHERE adminid=%d";

    public UserRepository() {
        logs = Logs.getInstance().init(ReserveRepository.class.getName());
    }

    @Override
    public Restaurant adminMetaData(int userId) {
        Optional<Connection> connection = _connection.open();
        Restaurant restaurant = new Restaurant();
        connection.ifPresent(con -> {
            String query = String.format(_ADMIN_RESTAURANT_INFO, userId);
            Statement statement = null;
            try {
                statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet.next()) {
                    restaurant.setRestaurantName(resultSet.getString("restname"));
                    restaurant.setImageUrl(resultSet.getString("imageurl"));
                    restaurant.setId(resultSet.getInt("restid"));
                }
            } catch (SQLException ex) {
                logs.errorLog(ex.toString());
            } finally {
                _connection.close();
            }

        });
        return restaurant;
    }
}
