package services;

import interfaces.ICrudOperation;
import repository.contracts.IAuthRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/users")
public class UsersService implements ICrudOperation {

    @Inject
    IAuthRepository userRepository;

    @POST
    @Override
    public Response create(Object object) {
        return null;
    }

    @PUT
    @Path("{id}")
    @Override
    public Response update(Object object) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @Override
    public Response delete() {
        return null;
    }

    @GET
    @Path("{id}")
    @Override
    public Response read() {
        return null;
    }

    @GET
    @Override
    public Response readList() {
        return null;
    }
}
