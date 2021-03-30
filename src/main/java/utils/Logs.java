package utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logs {

    private static Logs instance;
    private Logger logger;

    public static Logs getInstance() {
        if (instance == null) {
            instance = new Logs();
        }
        return instance;
    }

    public Logs init(String className) {
        logger = Logger.getLogger(className);
        logger.setLevel(Level.FINE);
        return this;
    }

    public Logger getLogger() {
        return logger;
    }

    public void infoLog(String message) {
        logger.info(message);
    }

    public void errorLog(String message) {
        logger.log(Level.SEVERE, message);
    }
}
