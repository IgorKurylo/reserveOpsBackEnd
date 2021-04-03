package models.response;

import com.google.gson.annotations.SerializedName;
import models.Reserve;

import java.util.List;

public class ReserveListResponse {

    @SerializedName("reserves")
    private List<Reserve> reserve;

    public ReserveListResponse(List<Reserve> reserve) {
        this.reserve = reserve;
    }

    public List<Reserve> getReserve() {
        return reserve;
    }

    public void setReserve(List<Reserve> reserve) {
        this.reserve = reserve;
    }
}

