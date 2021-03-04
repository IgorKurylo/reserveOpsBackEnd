package repository;

import repository.contracts.IAuthRepository;
import repository.contracts.IDatabaseConnection;
import models.User;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.sql.Connection;


@Service
public class AuthRepository implements IAuthRepository {

    @Inject
    IDatabaseConnection _connection;
    @Override
    public User authentication(String username, String password) {
        Connection connection = this._connection.open();
        if (connection != null) {
            System.out.println("Login");
        }
        this._connection.close();
        return null;
    }

    @Override
    public int signUp(User user) {
        return 1;
    }
}
