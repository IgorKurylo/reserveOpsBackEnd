package services;

import models.BaseResponse;
import models.ReservationWeek;
import models.Reserve;
import models.Restaurant;
import models.requests.NotUpcomingReserve;
import models.response.AdminStatisticResponse;
import models.response.StatisticResponse;
import repository.contracts.IStatisticRepository;
import security.Authorizer;
import utils.Const;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Statistics service which create data for user & admin statistics dashboards
 */
@Path("statistics")
@Authorizer
public class StatisticService {

    @Inject
    IStatisticRepository _repository;

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response statisticUser(@HeaderParam(Const.X_USER_ID) String user) {
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response statisticAdmin(@QueryParam("restaurantId") int restaurantId) {
        int todayReservation = _repository.todayReservation(restaurantId);
        int pendingReservation = _repository.pendingReservation(restaurantId);
        List<ReservationWeek> reservationWeekList = _repository.reservationsStatistic(restaurantId);
        AdminStatisticResponse statisticResponse = new AdminStatisticResponse(todayReservation, pendingReservation, reservationWeekList);
        BaseResponse<AdminStatisticResponse> response = new BaseResponse<>(statisticResponse, "", true);

        return new RestResponseBuilder(200).withEntity(response).create();
    }


}
