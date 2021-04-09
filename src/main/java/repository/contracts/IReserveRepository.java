package repository.contracts;

import exceptions.CreateReserveException;
import models.AvailableTime;
import models.Reserve;

import java.util.List;

public interface IReserveRepository {

    Reserve create(Reserve reserve,int userId) throws CreateReserveException;

    Reserve update(Reserve reserve);

    void delete(int id) throws CreateReserveException;

    Reserve getReserve(int id);

    List<Reserve> getReserves(int userId);

    List<AvailableTime> availableTimes(int restaurantId, String date);


}
