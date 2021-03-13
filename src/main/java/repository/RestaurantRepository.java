package repository;

import models.Restaurant;
import repository.contracts.IRestaurantRepository;

import java.util.List;

public class RestaurantRepository implements IRestaurantRepository {
    @Override
    public List<Restaurant> getRestaurants(int area) {
        return null;
    }
}
