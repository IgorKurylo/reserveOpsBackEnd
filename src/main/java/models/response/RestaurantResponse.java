package models.response;

import com.google.gson.annotations.SerializedName;
import models.Restaurant;

import java.util.List;

public class RestaurantResponse {
    @SerializedName("restaurants")
    public List<Restaurant> restaurantList;

    public RestaurantResponse(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public List<Restaurant> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }
}
