package services;

import interfaces.IAuthRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthService {
    @Inject
    IAuthRepository repository;

    @GET
    public Response authentication() {
        repository.login();
        return Response.status(200).build();
    }
}
