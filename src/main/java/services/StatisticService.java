package services;

import models.BaseResponse;
import models.Reserve;
import models.Restaurant;
import models.requests.NotUpcomingReserve;
import models.response.StatisticResponse;
import repository.contracts.IStatisticRepository;
import security.Authorizer;
import utils.Const;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("statistics")
@Authorizer
public class StatisticService {

    @Inject
    IStatisticRepository _repository;

    @GET
    @Path("user")
    public Response statisticUser(@HeaderParam(Const.X_USER_DATA) String user) {
        int userId = Integer.parseInt(user);
        int reservation = _repository.reservations(userId);
        Reserve reserve = new Reserve();
        try {
            reserve = _repository.upComing(userId);
        } catch (NotUpcomingReserve ex) {
            reserve = null;
        }
        Restaurant restaurant = _repository.lastVisit(userId);
        StatisticResponse statisticResponse = new StatisticResponse(reserve, reservation, restaurant);
        BaseResponse<StatisticResponse> response = new BaseResponse<>(statisticResponse, "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }

    @GET
    @Path("admin")
    public Response statisticAdmin(@HeaderParam(Const.X_USER_DATA) String user) {
        int userId = Integer.parseInt(user);
        return null;
    }


}
