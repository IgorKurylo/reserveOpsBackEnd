package repository;

import interfaces.IDatabaseConnection;
import interfaces.IAuthRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.sql.Connection;


@Service
public class AuthRepository implements IAuthRepository {

    @Inject
    IDatabaseConnection _connection;

    @Override
    public void login() {

        Connection connection = this._connection.open();
        if (connection != null) {
            System.out.println("Login");
        }


    }
}
