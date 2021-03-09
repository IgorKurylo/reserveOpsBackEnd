package application;


import org.glassfish.jersey.server.ResourceConfig;
import security.AuthenticationFilter;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

@ApplicationPath("/api")
public class Application extends ResourceConfig {

    public Application() {

        register(new ApplicationBinder());
        register(AuthenticationFilter.class);
        register(GsonProvider.class);
        try {
            ApplicationConfig.getInstance().loadConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}