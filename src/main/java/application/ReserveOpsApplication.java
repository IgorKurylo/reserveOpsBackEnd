package application;

import interfaces.IUserRepository;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class ReserveOpsApplication extends ResourceConfig {

    public ReserveOpsApplication() {
        register(new ApplicationBinder());
    }
}