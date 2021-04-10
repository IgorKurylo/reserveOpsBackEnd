package models.response;

import com.google.gson.annotations.SerializedName;
import models.ReservationWeek;

import java.util.List;

public class AdminStatisticResponse {

    @SerializedName("todayReservation")
    public int todayReservation;
    @SerializedName("pendingReservation")
    public int pendingReservation;
    @SerializedName("weekReservations")
    public List<ReservationWeek> reservationWeekList;

    public AdminStatisticResponse(int todayReservation, int pendingReservation, List<ReservationWeek> reservationWeekList) {
        this.todayReservation = todayReservation;
        this.pendingReservation = pendingReservation;
        this.reservationWeekList = reservationWeekList;
    }
}
