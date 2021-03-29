package models;

public class Reserve {

    private int Id;
    private String date;
    private String time;
    private User user;
    private Restaurant restaurant;
    private int guest;
    private int tableId;
    private ReserveStatus reserveStatus;

    public Reserve(int id, String date, String time, User user, Restaurant restaurant, int guest, int tableId, ReserveStatus orderStatus) {
        Id = id;
        this.date = date;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
        this.guest = guest;
        this.tableId = tableId;
        this.reserveStatus = orderStatus;
    }

    public Reserve() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
