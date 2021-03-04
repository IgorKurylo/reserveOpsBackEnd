package repository.contracts;

import java.sql.Connection;

public interface IDatabaseConnection {
    Connection open();
    void close();
}
