package services;

import interfaces.ICrudBaseOperation;
import models.AvailableTime;
import models.BaseResponse;
import models.Reserve;
import models.response.AvailableTimeResponse;
import repository.contracts.IReserveRepository;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("reserve")
public class ReserveService implements ICrudBaseOperation<Reserve> {

    @Inject
    IReserveRepository _repository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Reserve reserve) {
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
    public Response reservesList(@QueryParam("date") String date) {
        List<Reserve> orders = _repository.getReserves(0, date, 1);
        return new RestResponseBuilder(200).withEntity(orders).create();
    }

    @GET
    @Path("availableTimes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response availableTimes(@QueryParam("restaurantId") int restaurantId, @QueryParam("date") String date) {
        List<AvailableTime> availableTimeList = _repository.availableTimes(restaurantId, date);
        BaseResponse<AvailableTimeResponse> response = new BaseResponse<>(
                new AvailableTimeResponse(availableTimeList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }

    @GET()
    @Path("next")
    @Produces(MediaType.APPLICATION_JSON)
    public Response upComingReserve() {
        return null;
    }


    @Override
    public Response readList() {
        return null;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Reserve r) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response delete() {
        return null;
    }


}
