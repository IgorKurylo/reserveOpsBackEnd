package models.user;

import com.google.gson.annotations.SerializedName;

public class AuthCredentials {
    @SerializedName("phoneNumber")
    String phoneNumber;

    public AuthCredentials(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "AuthCredentials{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
