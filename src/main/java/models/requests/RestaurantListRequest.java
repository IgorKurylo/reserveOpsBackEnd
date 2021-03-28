package models.requests;

import com.google.gson.annotations.SerializedName;
import models.Area;

import java.util.List;

public class RestaurantListRequest {

    @SerializedName("areas")
    List<Area> areas;

    public RestaurantListRequest(List<Area> areas) {
        this.areas = areas;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}
