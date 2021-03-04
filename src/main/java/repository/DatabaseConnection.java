package repository;

import application.ApplicationConfig;
import repository.contracts.IDatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection implements IDatabaseConnection {
    private final String url;
    private final String user;
    private final String password;
    Connection connection = null;

    public DatabaseConnection() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        url = config.getValue("connection_string");
        user = config.getValue("db_user");
        password = config.getValue("db_password");
    }
    public Connection open() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.printf("Connected to %s", url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
