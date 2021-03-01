package services;

import interfaces.IBaseCrudOperation;
import interfaces.IAuthRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersService implements IBaseCrudOperation {

    @Inject
    IAuthRepository userRepository;


    @Override
    @POST
    public Response create() {
        return null;
    }

    @Override
    @PUT
    public Response update() {
        return null;
    }

    @DELETE
    @Override
    public Response delete() {
        return null;
    }

    @Override
    @GET()
    @Path("{id}")
    public Response read() {
        return null;
    }

    @Override
    @GET
    public Response readList() {
        return null;
    }
}
