package application;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;

@ApplicationPath("/api")
public class Application extends ResourceConfig {

    public Application() {
        register(new ApplicationBinder());
        try {
            ApplicationConfig.getInstance().loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}