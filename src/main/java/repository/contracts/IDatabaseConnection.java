package repository.contracts;

import java.sql.Connection;
import java.util.Optional;

public interface IDatabaseConnection {
    Optional<Connection> open();
    void close();
}
