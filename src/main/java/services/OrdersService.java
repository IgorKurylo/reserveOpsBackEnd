package services;

import interfaces.ICrudBaseOperation;
import models.AvailableTime;
import models.BaseResponse;
import models.Order;
import models.response.AvailableTimeResponse;
import repository.contracts.IOrderRepository;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("order")
public class OrdersService implements ICrudBaseOperation<Order> {

    @Inject
    IOrderRepository _repository;

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
    public Response read(int Id) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response readList() {
        List<Order> orders = _repository.getOrders();
        return new RestResponseBuilder(200).withEntity(orders).create();
    }
    @GET
    @Path("availableTimes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response availableTimes(@QueryParam("restaurantId") int restaurantId ,@QueryParam("date") String date) {
        List<AvailableTime> availableTimeList = _repository.availableTimes(restaurantId,date);
        BaseResponse<AvailableTimeResponse> response = new BaseResponse<>(
                new AvailableTimeResponse(availableTimeList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }
}
