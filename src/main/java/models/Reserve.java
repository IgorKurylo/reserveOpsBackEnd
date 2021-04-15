package models;

import com.google.gson.annotations.SerializedName;

public class Reserve {

    private int Id;
    @SerializedName("Date")
    private String date;
    @SerializedName("Time")
    private String time;
    @SerializedName("Restaurant")
    private Restaurant restaurant;
    @SerializedName("Guests")
    private int guest;
    @SerializedName("comment")
    private String comment;
    @SerializedName("Status")
    private String status;
    private int tableId;

    private ReserveStatus reserveStatus;

    public Reserve(String date, String time, Restaurant restaurant, int guest, int tableId, ReserveStatus reserveStatus, String comment) {
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
        this.guest = guest;
        this.tableId = tableId;
        this.reserveStatus = reserveStatus;
        this.comment = comment;
    }


    public Reserve(int id) {
        Id = id;
    }

    public Reserve(int id, String status) {
        Id = id;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Reserve() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public int getGuest() {
        return guest;
    }

    public void setGuest(int guest) {
        this.guest = guest;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public ReserveStatus getReserveStatus() {
        return reserveStatus;
    }

    public void setReserveStatus(ReserveStatus reserveStatus) {
        this.reserveStatus = reserveStatus;
    }

    @Override
    public String toString() {
        return "Reserve{" +
                "Id=" + Id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", restaurant=" + restaurant +
                ", guest=" + guest +
                ", comment='" + comment + '\'' +
                ", tableId=" + tableId +
                ", reserveStatus=" + reserveStatus +
                '}';
    }
}
