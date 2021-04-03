package services;

import interfaces.ICrudBaseOperation;
import models.AvailableTime;
import models.BaseRequest;
import models.BaseResponse;
import models.Reserve;
import models.response.AvailableTimeResponse;
import models.response.ReserveListResponse;
import models.response.ReserveResponse;
import repository.contracts.IReserveRepository;
import security.Authorizer;
import utils.Const;
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
    @Authorizer
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response create(Reserve reserve, @HeaderParam(Const.X_USER_DATA) String user) {
        Reserve newReserve = _repository.create(reserve, Integer.parseInt(user));
        BaseResponse<ReserveResponse> response = new BaseResponse<>(new ReserveResponse(newReserve), "", true);
        return new RestResponseBuilder(201).withEntity(response).create();

    }

    @GET
    @Path("{id}")
    @Authorizer
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response read(int Id, @HeaderParam(Const.X_USER_DATA) String user) {
        return null;
    }

    @GET
    @Authorizer
    @Produces(MediaType.APPLICATION_JSON)
    public Response reservesList(@QueryParam("date") String date, @HeaderParam(Const.X_USER_DATA) String user) {
        List<Reserve> reserveList = _repository.getReserves(date, Integer.parseInt(user));
        BaseResponse<ReserveListResponse> response = new BaseResponse<>
                (new ReserveListResponse(reserveList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }

    @GET
    @Path("availableTimes")
    @Authorizer
    @Produces(MediaType.APPLICATION_JSON)
    public Response availableTimes(@QueryParam("restaurantId") int restaurantId, @QueryParam("date") String date) {
        List<AvailableTime> availableTimeList = _repository.availableTimes(restaurantId, date);
        BaseResponse<AvailableTimeResponse> response = new BaseResponse<>(
                new AvailableTimeResponse(availableTimeList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }

    @GET()
    @Path("next")
    @Authorizer
    @Produces(MediaType.APPLICATION_JSON)
    public Response upComingReserve() {
        return null;
    }


    @Override
    public Response readList(@HeaderParam(Const.X_USER_DATA) String user) {
        return null;
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response update(Reserve r, @HeaderParam(Const.X_USER_DATA) String user) {
        return null;
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Override
    public Response delete(int id, @HeaderParam(Const.X_USER_DATA) String user) {
        return null;
    }


}
