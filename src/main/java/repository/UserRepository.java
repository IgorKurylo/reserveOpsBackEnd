package repository;

import application.ApplicationConfig;
import interfaces.IUserRepository;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.io.IOException;


@Service
public class UserRepository implements IUserRepository {
    @Inject
    ApplicationConfig config;

    @Override
    public void login() {
        System.out.println("Login Work Injection");
        System.out.println(config.getValue("connection_string"));

    }
}
