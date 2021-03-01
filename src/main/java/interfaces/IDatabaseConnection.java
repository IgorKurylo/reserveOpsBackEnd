package interfaces;

import java.sql.Connection;

public interface IDatabaseConnection {
    Connection open();
    void close();
}
