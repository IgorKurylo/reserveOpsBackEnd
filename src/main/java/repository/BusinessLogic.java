package repository;

import models.RestaurantTables;

import java.util.List;

public class BusinessLogic {

    public static RestaurantTables findTable(int guestRequestNumber, List<RestaurantTables> tables) {
        RestaurantTables t = null;
        for (RestaurantTables resTable : tables) {
            if (resTable.getNumOfSeats() >= guestRequestNumber || resTable.getNumOfSeats() <= guestRequestNumber) {
                t = resTable;
            }
        }
        return t;
    }
}
