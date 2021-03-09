package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthCredentials {
    @JsonProperty("userName")
    String userName;
    @JsonProperty("password")
    String password;

    public AuthCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
