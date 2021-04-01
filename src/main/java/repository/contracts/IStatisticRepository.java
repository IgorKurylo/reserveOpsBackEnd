package repository.contracts;

import models.Reserve;
import models.Restaurant;
import models.response.StatisticResponse;

public interface IStatisticRepository {

    Reserve upComing(int userId);

    int reservations(int userId);

    Restaurant lastVisit(int userId);
}
