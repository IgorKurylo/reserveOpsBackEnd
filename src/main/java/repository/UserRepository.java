package repository;

import interfaces.IUserRepository;
import org.jvnet.hk2.annotations.Service;


@Service
public class UserRepository implements IUserRepository {
    @Override
    public void login() {
        System.out.println("Login Work Injection");
    }
}
