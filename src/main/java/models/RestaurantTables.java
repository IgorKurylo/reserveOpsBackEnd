package models;

public class RestaurantTables {
    int numOfSeats;
    int tableId;

    public RestaurantTables(int numOfSeats, int tableId) {
        this.numOfSeats = numOfSeats;
        this.tableId = tableId;
    }

    public int getNumOfSeats() {
        return numOfSeats;
    }

    public void setNumOfSeats(int numOfSeats) {
        this.numOfSeats = numOfSeats;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }
}
