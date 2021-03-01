package application;

import interfaces.IDatabaseConnection;
import interfaces.IAuthRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import repository.DatabaseConnection;
import repository.AuthRepository;


public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AuthRepository.class).to(IAuthRepository.class);
        bind(DatabaseConnection.class).to(IDatabaseConnection.class);
    }
}
