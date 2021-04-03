package repository;

import models.Reserve;
import models.Restaurant;
import models.response.StatisticResponse;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IStatisticRepository;
import utils.Logs;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class StatisticRepository implements IStatisticRepository {

    @Inject
    IDatabaseConnection _connection;
    private final String _UPCOMING_QUERY = "SELECT reservedate as date, to_char(reservetime,'HH24:MM') as time, guests, restname " +
            "FROM reserve " +
            "INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate > CURRENT_DATE " +
            "and usrid = %d order by reservedate asc LIMIT 1 ";
    private final String _RESERVATION_QUERY = "SELECT count(reserve.id) reservationCount FROM reserve WHERE usrid = %d";
    private final String _LAST_VISIT_QUERY = "SELECT  restname,reservedate " +
            "FROM reserve INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate < CURRENT_DATE " +
            "and usrid = %d order by reservedate desc LIMIT 1";

    private Logs logs;

    public StatisticRepository() {
        logs = Logs.getInstance().init(ReserveRepository.class.getName());
    }

    @Override
    public Reserve upComing(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_UPCOMING_QUERY, userId);
        Reserve reserve = new Reserve();
        connection.ifPresent(conn -> {
            Statement statement = null;
            try {
                statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                resultSet.next();
                reserve.setDate(resultSet.getString("date"));
                reserve.setTime(resultSet.getString("time"));
                reserve.setGuest(resultSet.getInt("guests"));
                reserve.setRestaurant(new Restaurant(resultSet.getString("restname")));

            } catch (SQLException ex) {
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
}
