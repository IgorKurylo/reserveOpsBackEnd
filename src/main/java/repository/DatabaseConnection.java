package repository;

import application.ApplicationConfig;
import repository.contracts.IDatabaseConnection;
import utils.Logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseConnection implements IDatabaseConnection {
    private final String url;
    private final String user;
    private final String password;
    Optional<Connection> connection;
    private Logs logs;

    public DatabaseConnection() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        url = config.getValue("connection_string");
        user = config.getValue("db_user");
        password = config.getValue("db_password");
        logs = Logs.getInstance().init(DatabaseConnection.class.getName());
    }

    public Optional<Connection> open() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = Optional.ofNullable(DriverManager.getConnection(url, user, password));
            logs.infoLog(String.format("Connected to %s \n", url));
        } catch (SQLException | ClassNotFoundException e) {
            logs.errorLog(e.getMessage());
        }
        return connection;
    }

    public void close() {
        try {
            if (connection.isPresent()) {
                connection.get().close();
            }
        } catch (SQLException e) {
            logs.errorLog(e.getMessage());
        }
    }
}
