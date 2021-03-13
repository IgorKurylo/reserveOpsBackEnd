package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import exceptions.InvalidTokenException;
import models.Role;
import models.User;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class AccessTokenGenerator {
    private Algorithm algorithm;

    public AccessTokenGenerator(String secret) throws UnsupportedEncodingException {
        algorithm = Algorithm.HMAC256(secret);
    }

    public String generate(AuthenticationTokenDetails details) {
        String token = null;

        token = JWT.create()
                .withIssuer(details.getIssuer())
                .withSubject(details.getUser().getUserName())
                .withIssuedAt(Date.from(details.getIssuedAt().toInstant()))
                .withExpiresAt(Date.from(details.getExpiredAt().toInstant()))
                .withClaim("Id", details.getUser().getId())
                .withClaim("Role", details.getUser().getRole().name())
                .sign(algorithm);

        return token;
    }

    public AuthenticationTokenDetails parse(String token) throws InvalidTokenException {
        AuthenticationTokenDetails details = null;
        try {
            DecodedJWT jwt = JWT.decode(token);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(jwt.getIssuer()).build();
            DecodedJWT verifiedJWT = verifier.verify(token);
            if (verifiedJWT != null) {
                details = new AuthenticationTokenDetails(new User(jwt.getClaim("Id").asInt(),
                        jwt.getSubject(), Role.valueOf(jwt.getClaim("Role").asString())));
            }
        } catch (JWTVerificationException ex) {
            throw new InvalidTokenException(String.format("Invalid Token Cause %s", ex.toString()));
        }
        return details;
    }
}
