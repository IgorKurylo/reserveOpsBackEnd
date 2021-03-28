package services;

import interfaces.ICrudBaseOperation;
import interfaces.ICurdExtendOperation;
import models.*;
import models.requests.RestaurantListRequest;
import models.response.AvailableTimeResponse;
import models.response.RestaurantResponse;
import repository.contracts.IRestaurantRepository;
import utils.IConverter;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("restaurant")
public class RestaurantService implements ICrudBaseOperation<Restaurant> {
    @Inject
    IRestaurantRepository _repository;

    @Override
    public Response create(Restaurant object) {
        return null;
    }

    @Override
    public Response update(Restaurant object) {
        return null;
    }

    @Override
    public Response delete() {
        return null;
    }

    @Override
    public Response read(int id) {
        return null;
    }

    @Override
    public Response readList() {
        return null;
    }

    @POST
    @Path("list")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByAreas(RestaurantListRequest request) {

        List<Restaurant> restaurantList = _repository.getRestaurants(request.getAreas());
        BaseResponse<RestaurantResponse> response =
                new BaseResponse<>(new RestaurantResponse(restaurantList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }




}
