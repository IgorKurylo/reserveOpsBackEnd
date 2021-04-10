package security;

import application.ApplicationConfig;
import exceptions.InvalidTokenException;
import utils.Const;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Provider
@Authorizer
@Priority(Priorities.AUTHENTICATION)
public class AuthorizerFilter implements ContainerRequestFilter {

    AccessTokenGenerator tokenGenerator;

    public AuthorizerFilter() {
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
                requestContext.getHeaders().add(Const.X_USER_ID, String.valueOf(details.getUser().getId()));
                requestContext.getHeaders().add(Const.X_USER_ROLE, details.getUser().getRole().name());

            } catch (InvalidTokenException e) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Unauthorized").build());
            }
        } else {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Unauthorized").build());

        }
    }
}
