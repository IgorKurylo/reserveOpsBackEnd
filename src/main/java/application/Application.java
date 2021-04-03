package application;


import org.glassfish.jersey.server.ResourceConfig;
import security.AuthorizerFilter;

import javax.ws.rs.ApplicationPath;
import java.io.IOException;

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
    }


}