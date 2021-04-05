package models.requests;

import com.google.gson.annotations.SerializedName;
import models.Reserve;

public class ReserveRequest {

    @SerializedName("reserve")
    private Reserve reserve;

    public ReserveRequest(Reserve reserve) {
        this.reserve = reserve;
    }

    public Reserve getReserve() {
        return reserve;
    }

    public void setReserve(Reserve reserve) {
        this.reserve = reserve;
    }
}
