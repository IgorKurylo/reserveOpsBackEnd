package utils;

import exceptions.ReserveApproveException;
import repository.DatabaseConnection;
import repository.ReserveRepository;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IReserveRepository;

import javax.inject.Inject;
import java.util.TimerTask;

public class ReservationScheduler extends TimerTask {

    IReserveRepository repository;
    IDatabaseConnection connection;
    private final Logs logs;

    public ReservationScheduler() {

        logs = Logs.getInstance().init(ReservationScheduler.class.getName());
        repository = new ReserveRepository();
        connection = new DatabaseConnection();
    }

    @Override
    public void run() {
        logs.infoLog("Task Started " + System.currentTimeMillis());
        try {
            repository.approveAll(connection);
        } catch (ReserveApproveException e) {
            logs.errorLog(e.getMessage());
        }
        logs.infoLog("Task Completed" + System.currentTimeMillis());
    }
}
