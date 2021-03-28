package repository;

import models.Area;
import models.AvailableTime;
import models.Restaurant;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IRestaurantRepository;
import utils.Converters;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements IRestaurantRepository {

    final String RESTAURANT_QUERY = "SELECT RestId,RestName,RestArea,WorkTimeStart,WorkTimeEnd,Address," +
            "ImageURL,WebSite,PhoneNo FROM Restaurant";
    final String WHERE = " WHERE RestArea IN (%s)";
    @Inject
    IDatabaseConnection _connection;

    @Override
    public List<Restaurant> getRestaurants(List<Area> areas) {
        List<Restaurant> restaurantList = new ArrayList<>();
        Optional<Connection> connection = this._connection.open();
        String _restaurantQuery = "";
        if (areas.size() > 0) {
            _restaurantQuery = String.format(RESTAURANT_QUERY + WHERE, joinAreas(areas));
        } else {
            _restaurantQuery = RESTAURANT_QUERY;
        }
        String final_restaurantQuery = _restaurantQuery;
        connection.ifPresent(conn -> {
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(final_restaurantQuery);
                while (resultSet.next()) {
                    Restaurant restaurant = new Restaurant(resultSet.getInt("RestId"),
                            resultSet.getString("RestName"),
                            resultSet.getString("RestArea"),
                            resultSet.getString("Address"),
                            resultSet.getString("ImageURL"),
                            new ArrayList<>(),
                            Converters.ConvertTimeToString(resultSet.getTime("WorkTimeStart")),
                            Converters.ConvertTimeToString(resultSet.getTime("WorkTimeEnd")),
                            resultSet.getString("PhoneNo"),
                            resultSet.getString("WebSite"));
                    restaurantList.add(restaurant);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
        });

        return restaurantList;
    }



    private String joinAreas(List<Area> areas) {
        StringBuilder builder = new StringBuilder();
        for (Area area : areas) {
            builder.append(String.format("'%s'",area.getName()));
            builder.append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
