package repository;

import models.Reserve;
import models.Restaurant;
import models.response.StatisticResponse;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IStatisticRepository;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.Optional;

public class StatisticRepository implements IStatisticRepository {

    @Inject
    IDatabaseConnection _connection;
    private final String _UPCOMING_QUERY = "SELECT reservedate, reservetime, guests, restname " +
            "FROM reserve " +
            "INNER JOIN restaurant r on r.restid = reserve.restid " +
            "WHERE reservedate > CURRENT_DATE " +
            "and usrid = %d order by reservedate asc LIMIT 1 ";
    private final String _RESERVATION_QUERY = "SELECT count(reserve.id) reservationCount FROM reserve WHERE usrid = %d";
    private final String _LAST_VISIT_QUERY = "SELECT  restname,reservedate" +
            "FROM reserve INNER JOIN restaurant r on r.restid = reserve.restid" +
            "WHERE reservedate < CURRENT_DATE " +
            "and usrid = %d order by reservedate desc LIMIT 1";


    @Override
    public Reserve upComing(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_UPCOMING_QUERY, userId);

        return null;
    }

    @Override
    public int reservations(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_RESERVATION_QUERY, userId);
        return 0;
    }

    @Override
    public Restaurant lastVisit(int userId) {
        Optional<Connection> connection = this._connection.open();
        String query = String.format(_LAST_VISIT_QUERY, userId);
        return null;
    }
}
