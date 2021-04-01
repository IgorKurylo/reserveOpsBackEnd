package models.response;

import com.google.gson.annotations.SerializedName;
import models.Reserve;
import models.Restaurant;

public class StatisticResponse {

    @SerializedName("upComingReserve")
    private Reserve reserve;
    @SerializedName("numberOfReservation")
    private int numberOfReservation;
    @SerializedName("lastRestaurantVisit")
    private Restaurant restaurant;

    public StatisticResponse(Reserve reserve, int numberOfReservation, Restaurant restaurant) {
        this.reserve = reserve;
        this.numberOfReservation = numberOfReservation;
        this.restaurant = restaurant;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }

    public int getNumberOfReservation() {
        return numberOfReservation;
    }

    public void setNumberOfReservation(int numberOfReservation) {
        this.numberOfReservation = numberOfReservation;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
