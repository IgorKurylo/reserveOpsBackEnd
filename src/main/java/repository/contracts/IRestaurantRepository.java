package repository.contracts;

import models.Area;
import models.AvailableTime;
import models.Restaurant;

import java.util.List;

public interface IRestaurantRepository {

    List<Restaurant> getRestaurants(List<Area> areas);



}
