package services;

import interfaces.ICrudBaseOperation;
import models.*;
import models.requests.RestaurantListRequest;
import models.response.RestaurantResponse;
import repository.contracts.IRestaurantRepository;
import security.Authorizer;
import utils.RestResponseBuilder;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 *  Restaurant service which handle all restaurant operations
 */
@Path("restaurant")
public class RestaurantService {
    @Inject
    IRestaurantRepository _repository;

    @POST
    @Path("list")
    @Authorizer
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response listByAreas(RestaurantListRequest request) {

        List<Restaurant> restaurantList = _repository.getRestaurants(request.getAreas());
        BaseResponse<RestaurantResponse> response =
                new BaseResponse<>(new RestaurantResponse(restaurantList), "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }


}
