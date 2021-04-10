package repository.contracts;

import models.ReservationWeek;
import models.Reserve;
import models.Restaurant;
import models.requests.NotUpcomingReserve;
import models.response.StatisticResponse;

import java.util.List;

public interface IStatisticRepository {

    Reserve upComing(int userId) throws NotUpcomingReserve;

    int reservations(int userId);

    Restaurant lastVisit(int userId);

    List<ReservationWeek> reservationsStatistic(int restaurantId);

    int todayReservation(int restaurantId);

    int pendingReservation(int restaurantId);
}
