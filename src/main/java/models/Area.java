package models;

import com.google.gson.annotations.SerializedName;

public class Area {
    @SerializedName("name")
    private String name;

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
