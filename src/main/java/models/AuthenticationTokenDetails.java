package models;

import application.ApplicationConfig;

import java.time.ZonedDateTime;

public class AuthenticationTokenDetails {

    User user;
    ZonedDateTime issuedAt;
    ZonedDateTime expiredAt;
    String issuer;
    String tokenSecret;

    public AuthenticationTokenDetails(User user, ZonedDateTime issuedAt, ZonedDateTime expiredAt, String issuer, String tokenSecret) {
        this.user = user;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
        this.issuer = issuer;
        this.tokenSecret = ApplicationConfig.getInstance().getValue("token_secret");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(ZonedDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public ZonedDateTime getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(ZonedDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
