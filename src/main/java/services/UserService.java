package services;

import models.BaseResponse;
import models.Restaurant;
import models.Role;
import models.response.AdminMetaDataResponse;
import models.response.AdminStatisticResponse;
import repository.contracts.IUserRepository;
import security.Authorizer;
import utils.Const;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *  User service which handle operation for user
 */
@Path("user")
@Authorizer
public class UserService {

    @Inject
    IUserRepository repository;

    @GET
    @Path("metadata")
    public Response metadata(@HeaderParam(Const.X_USER_ID) int userId,
                             @HeaderParam(Const.X_USER_ROLE) String role) {
        Restaurant restaurant = null;
        Response response = null;
        if (Role.valueOf(role) == Role.Admin) {
            restaurant = repository.adminMetaData(userId);
            if (restaurant != null) {
                AdminMetaDataResponse statisticResponse=new AdminMetaDataResponse(restaurant);
                response = new RestResponseBuilder(200).withEntity(new BaseResponse<AdminMetaDataResponse>(statisticResponse, "", true)).create();

            } else {
                response = new RestResponseBuilder(400).withEntity(new BaseResponse<>(null, "error", false)).create();

            }
        }

        return response;


    }
}
