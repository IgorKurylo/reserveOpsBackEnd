package models;

public class AccessToken {

    String accessToken;
    int exp;

    public AccessToken(String accessToken, int exp) {
        this.accessToken = accessToken;
        this.exp = exp;
    }
}
