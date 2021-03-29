package repository;

import models.RestaurantTables;

import java.util.List;

public class BusinessLogic {

    public static RestaurantTables findTable(int guestRequestNumber, List<RestaurantTables> tables) {
        RestaurantTables t = null;
        for (RestaurantTables resTable : tables) {
            if (guestRequestNumber % 2 == 0 && guestRequestNumber == resTable.getNumOfSeats()) {
                t = resTable;
            } else if (Math.abs(guestRequestNumber - resTable.getNumOfSeats())  == 1) {
                t = resTable;
            }
        }
        return t;
    }
}
