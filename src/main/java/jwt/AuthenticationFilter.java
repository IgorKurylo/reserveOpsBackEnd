package jwt;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

public class AuthenticationFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String header = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")){

        }
    }
}
