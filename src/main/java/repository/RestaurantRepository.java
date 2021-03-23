package repository;

import models.Restaurant;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IRestaurantRepository;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements IRestaurantRepository {

    final String RESTAURANT_QUERY_AREA_FILTER = "SELECT * FROM reserveops.\"Restaurant\" WHERE RestArea=%d";
    final String RESTAURANT_QUERY = "SELECT * FROM reserveops.\"Restaurant\"";
    @Inject
    IDatabaseConnection _connection;

    @Override
    public List<Restaurant> getRestaurants(int area) {
        List<Restaurant> restaurantList = new ArrayList<>();
        Optional<Connection> connection = this._connection.open();

        if (area > 0) {
            String _restaurantQuery = String.format(RESTAURANT_QUERY_AREA_FILTER, area);
            connection.ifPresent(conn -> {
                Statement statement = null;
                try {
                    statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery(_restaurantQuery);
                    while (resultSet.next()){

                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    _connection.close();
                }
            });
        }
        return null;
    }
}
