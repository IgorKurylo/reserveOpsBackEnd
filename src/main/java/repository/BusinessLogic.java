package repository;

import models.RestaurantTables;

import java.util.List;

public class BusinessLogic {
    /**
     * find table by find the min of seats
     *
     * @param guestRequestNumber
     * @param tables
     * @return
     */
    public static RestaurantTables findTable(List<RestaurantTables> tables) {
        RestaurantTables t = null;
        int minIndex = 0;
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getNumOfSeats() < tables.get(minIndex).getNumOfSeats()) {
                minIndex = i;
            }
        }
        return tables.get(minIndex);
    }
}
