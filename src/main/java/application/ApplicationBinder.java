package application;

import repository.ReserveRepository;
import repository.RestaurantRepository;
import repository.contracts.IDatabaseConnection;
import repository.contracts.IAuthRepository;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import repository.DatabaseConnection;
import repository.AuthRepository;
import repository.contracts.IReserveRepository;
import repository.contracts.IRestaurantRepository;
import interfaces.IConverter;
import utils.JsonConverter;


public class ApplicationBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bind(AuthRepository.class).to(IAuthRepository.class);
        bind(DatabaseConnection.class).to(IDatabaseConnection.class);
        bind(JsonConverter.class).to(IConverter.class);
        bind(RestaurantRepository.class).to(IRestaurantRepository.class);
        bind(ReserveRepository.class).to(IReserveRepository.class);
    }
}
