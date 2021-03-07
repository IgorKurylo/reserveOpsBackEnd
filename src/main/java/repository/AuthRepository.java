package repository;

import models.AuthCredentials;
import models.Role;
import repository.contracts.IAuthRepository;
import repository.contracts.IDatabaseConnection;
import models.User;
import org.jvnet.hk2.annotations.Service;
import utils.Const;

import javax.inject.Inject;
import java.sql.*;
import java.util.Optional;


@Service
public class AuthRepository implements IAuthRepository {

    private String _AUTH_QUERY = "SELECT Id, UserName,Password FROM %s WHERE UserName = '%s'";
    @Inject
    IDatabaseConnection _connection;

    @Override
    public User authentication(AuthCredentials authCredentials) {
        Optional<Connection> connection = this._connection.open();
        String _authQuery = String.format(_AUTH_QUERY, Const.USER_TABLE,authCredentials.getUserName());
        User u = new User();
        connection.ifPresent(conn -> {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(_authQuery);

                while (resultSet.next()) {
                    String userName = resultSet.getString("UserName");
                    String passWord = resultSet.getString("Password");
                    //TODO: make password decrypt and verify
                    int Id = resultSet.getInt("Id");
                    u.setRole(Role.Admin);
                    u.setId(Id);
                    u.setUserName(userName);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        });
        return u;

    }
}
