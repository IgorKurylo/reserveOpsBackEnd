package repository.contracts;

import exceptions.ReserveException;
import models.AvailableTime;
import models.Reserve;

import java.util.List;

public interface IReserveRepository {

    int create(Reserve reserve);

    Reserve update(Reserve reserve);

    void delete(int id) throws ReserveException;

    Reserve getReserve(int id);

    List<Reserve> getReserves(int restaurantId, String date, int userId);

    List<AvailableTime> availableTimes(int restaurantId, String date);


}
