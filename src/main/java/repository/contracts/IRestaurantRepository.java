package repository.contracts;

import models.Restaurant;

import java.util.List;

public interface IRestaurantRepository {

    List<Restaurant> getRestaurants(List<String> areas);


}
