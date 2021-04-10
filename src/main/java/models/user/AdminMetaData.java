package models.user;

import com.google.gson.annotations.SerializedName;
import models.Restaurant;

public class AdminMetaData {
    @SerializedName("restaurant")
    private Restaurant restaurant;

    public AdminMetaData(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
