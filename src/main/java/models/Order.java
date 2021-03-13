package models;

public class Order {

    private int Id;
    private String date;
    private String time;
    private User user;
    private Restaurant restaurant;
    private int guest;
    private int tableId;
    private OrderStatus orderStatus;

    public Order(int id, String date, String time, User user, Restaurant restaurant, int guest, int tableId, OrderStatus orderStatus) {
        Id = id;
        this.date = date;
        this.time = time;
        this.user = user;
        this.restaurant = restaurant;
        this.guest = guest;
        this.tableId = tableId;
        this.orderStatus = orderStatus;
    }

    public Order() {
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
