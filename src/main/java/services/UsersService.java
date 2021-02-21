package services;

import interfaces.IBaseOperation;

import javax.ws.rs.*;
import java.util.List;

@Path("/users")
public class UsersService<E> implements IBaseOperation<E> {
    @Override
    @POST
    public int create() {
        return 0;
    }

    @Override
    @PUT
    public void update() {

    }

    @DELETE
    @Override
    public int delete() {
        return 0;
    }

    @Override
    @GET()
    @Path("{id}")
    public E read() {
        return null;
    }

    @Override
    @GET
    public List<E> readList() {
        return null;
    }
}
