package utils;

public class Logger {

    private Logger instance;

    public Logger getInstance() {
        if (instance == null) {
            return new Logger();
        }
        return instance;
    }

    public void debug(String log) {

    }

    public void info(String log) {

    }

    public void error(String log) {

    }

}
