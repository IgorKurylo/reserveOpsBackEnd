package services;

import interfaces.ICrudOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

public class OrdersService implements ICrudOperation {
    @POST
    @Override
    public Response create(Object o) {
        return null;
    }

    @PUT
    @Path("{id}")
    @Override
    public Response update(Object o) {
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
