package jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import models.AccessToken;
import models.AuthenticationTokenDetails;
import models.User;

import java.io.UnsupportedEncodingException;
import java.time.temporal.TemporalField;
import java.util.Date;

public class AccessTokenGenerator {

    public AccessToken generate(AuthenticationTokenDetails details) {
        AccessToken accessToken = null;
        try {
            String token = JWT.create()
                    .withIssuer(details.getIssuer())
                    .withIssuedAt(Date.from(details.getIssuedAt().toInstant()))
                    .withExpiresAt(Date.from(details.getExpiredAt().toInstant()))
                    .withClaim("Id", details.getUser().getId())
                    .sign(Algorithm.HMAC256(details.getTokenSecret()));
//            accessToken=new AccessToken(token,Date.from(details.getExpiredAt()))
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return accessToken;


    }
}
