package repository;

import application.ApplicationConfig;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    @Inject
    ApplicationConfig config;
    private String url;
    private String user;
    private String password;
    Connection connection = null;

    public DatabaseConnection() {
        url = config.getValue("connection_string");
        user = config.getValue("db_user");
        password = config.getValue("db_password");

    }

    public void openConnection() {

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.printf("Connected to %s",url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
