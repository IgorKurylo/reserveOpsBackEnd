package services;

import interfaces.ICrudOperation;
import models.Order;
import utils.RestResponseBuilder;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("orders")
public class OrdersService implements ICrudOperation<Order> {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Order order) {
        return null;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Order o) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response delete() {
        return null;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response read() {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response readList() {
        List<Order> orders = new ArrayList<>();
        Response response = new RestResponseBuilder(200).withEntity(orders).create();

        return response;
    }
}
