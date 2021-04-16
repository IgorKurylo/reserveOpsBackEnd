package application;


import org.glassfish.jersey.server.ResourceConfig;
import security.AuthorizerFilter;
import utils.ReservationScheduler;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

@ApplicationPath("/api")
public class Application extends ResourceConfig {

    public Application() {
        register(new ApplicationBinder());
        register(AuthorizerFilter.class);
        register(GsonProvider.class);
        try {
            ApplicationConfig.getInstance().loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScheduler();
    }

    private void setScheduler() {
        ApplicationConfig config = ApplicationConfig.getInstance();
        int hour = Integer.parseInt(config.getValue("reservation_updateAt"));
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        ReservationScheduler scheduler = new ReservationScheduler();
        Timer timer = new Timer("reservationUpdate");
        timer.schedule(scheduler, calendar.getTime());
    }


}