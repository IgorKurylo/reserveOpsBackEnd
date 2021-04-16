package repository;

import models.user.AuthCredentials;
import models.enums.Role;
import repository.contracts.IAuthRepository;
import repository.contracts.IDatabaseConnection;
import models.user.User;
import org.jvnet.hk2.annotations.Service;
import utils.Logs;

import javax.inject.Inject;
import java.sql.*;
import java.util.Optional;


@Service
public class AuthRepository implements IAuthRepository {

    private final String _AUTH_QUERY = "SELECT Id,firstName,lastName,role FROM \"Users\" WHERE phoneNumber = '%s'";
    private final String _REGISTER_QUERY = "INSERT INTO \"Users\" (firstName, lastName, phoneNumber, role) VALUES (?,?,?,?)";
    @Inject
    IDatabaseConnection _connection;
    Logs logs;

    public AuthRepository() {
        logs = Logs.getInstance().init(AuthRepository.class.getName());
    }

    @Override
    public User authentication(AuthCredentials authCredentials) {
        logs.infoLog(authCredentials.toString());
        Optional<Connection> connection = this._connection.open();
        String _authQuery = String.format(_AUTH_QUERY, authCredentials.getPhoneNumber());
        User u = new User();
        connection.ifPresent(conn -> {
            try {
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(_authQuery);

                while (resultSet.next()) {
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    int Id = resultSet.getInt("Id");
                    Role role = Role.valueOf(resultSet.getString("role"));
                    u.setRole(role);
                    u.setId(Id);
                    u.setFirstName(firstName);
                    u.setLastName(lastName);
                    u.setPhoneNumber(authCredentials.getPhoneNumber());
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                _connection.close();
            }
            logs.infoLog(u.toString());

        });
        return u;

    }

    @Override
    public int registration(User user) {
        int userId = -1;
        Optional<Connection> connection = this._connection.open();
        if (connection.isPresent()) {
            Connection conn = connection.get();
            try {
                PreparedStatement statement = conn.prepareStatement(_REGISTER_QUERY, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getPhoneNumber());
                statement.setString(4, Role.SimpleUser.toString());
                if (statement.executeUpdate() > 0) {
                    ResultSet generatedKeys = statement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);
                    }
                    logs.infoLog(String.format("User created id: %d", userId));
                }
            } catch (SQLException ex) {
                logs.errorLog(ex.getMessage());
            } finally {
                _connection.close();
            }
        }
        return userId;
    }
}
