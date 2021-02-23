package application;

import interfaces.IUserRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import repository.UserRepository;


public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(UserRepository.class).to(IUserRepository.class);
        bind(ApplicationConfig.getInstance()).to(ApplicationConfig.class);

    }
}
