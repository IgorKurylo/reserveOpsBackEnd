package security;

import application.ApplicationConfig;
import exceptions.InvalidTokenException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AuthenticationFilter implements ContainerRequestFilter {

    AccessTokenGenerator tokenGenerator;

    public AuthenticationFilter() {
        String secret = ApplicationConfig.getInstance().getValue("token_secret");
        try {
            tokenGenerator = new AccessTokenGenerator(secret);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
            try {
                AuthenticationTokenDetails details = tokenGenerator.parse(token);

            } catch (InvalidTokenException e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Unauthorized").build());
                return;
            }

        }
    }
}
