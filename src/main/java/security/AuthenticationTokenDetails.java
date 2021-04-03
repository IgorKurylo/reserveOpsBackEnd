package security;

import application.ApplicationConfig;
import models.user.User;
import utils.Const;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AuthenticationTokenDetails {

    User user;
    ZonedDateTime issuedAt;
    ZonedDateTime expiredAt;
    String issuer;
    String tokenSecret;

    public AuthenticationTokenDetails(User user) {
        this.user = user;
        this.issuedAt = ZonedDateTime.now();
        this.expiredAt = this.expiredAt();
        this.issuer = this.issuer();
        this.tokenSecret = ApplicationConfig.getInstance().getValue("token_secret");
    }

    private ZonedDateTime expiredAt() {
        ZonedDateTime expiredAt = ZonedDateTime.now();
        expiredAt = expiredAt.plusMinutes(Const.TOKEN_DELTA_EXPIRATION_TIME);
        return expiredAt;
    }

    private String issuer() {
        return UUID.randomUUID().toString();
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
