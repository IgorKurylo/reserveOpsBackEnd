package models.response;

import com.google.gson.annotations.SerializedName;
import models.AvailableTime;

import java.util.List;

public class AvailableTimeResponse
{
    @SerializedName("availableTimes")
    List<AvailableTime> availableTimeList;

    public AvailableTimeResponse(List<AvailableTime> availableTimeList) {
        this.availableTimeList = availableTimeList;
    }

    public List<AvailableTime> getAvailableTimeList() {
        return availableTimeList;
    }

    public void setAvailableTimeList(List<AvailableTime> availableTimeList) {
        this.availableTimeList = availableTimeList;
    }
}
