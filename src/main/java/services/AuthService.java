package services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")

public class AuthService {

    @POST
    public Response authentication(){
        return null;
    }
}
