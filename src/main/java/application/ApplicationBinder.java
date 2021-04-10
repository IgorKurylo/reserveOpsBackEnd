package application;

import repository.*;
import repository.contracts.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
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
        bind(StatisticRepository.class).to(IStatisticRepository.class);
        bind(UserRepository.class).to(IUserRepository.class);
    }
}
