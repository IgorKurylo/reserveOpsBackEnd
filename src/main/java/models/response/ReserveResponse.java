package models.response;

import com.google.gson.annotations.SerializedName;
import models.Reserve;

public class ReserveResponse {

    @SerializedName("reserve")
    private Reserve reserve;

    public ReserveResponse(Reserve reserve) {
        this.reserve = reserve;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }
}

