package repository.contracts;

import models.Reserve;
import models.Restaurant;
import models.requests.NotUpcomingReserve;
import models.response.StatisticResponse;

public interface IStatisticRepository {

    Reserve upComing(int userId) throws NotUpcomingReserve;

    int reservations(int userId);

    Restaurant lastVisit(int userId);
}
