package repository.contracts;

import exceptions.ReserveException;
import models.AvailableTime;
import models.Reserve;

import java.util.List;

public interface IReserveRepository {

    Reserve create(Reserve reserve,int userId);

    Reserve update(Reserve reserve);

    void delete(int id) throws ReserveException;

    Reserve getReserve(int id);

    List<Reserve> getReserves(String date, int userId);

    List<AvailableTime> availableTimes(int restaurantId, String date);


}
