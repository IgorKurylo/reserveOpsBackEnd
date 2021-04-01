package services;

import models.BaseResponse;
import models.Reserve;
import models.Restaurant;
import models.response.StatisticResponse;
import repository.contracts.IStatisticRepository;
import utils.RestResponseBuilder;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("statistics")
public class StatisticService {

    @Inject
    IStatisticRepository _repository;

    @GET
    public Response statistics() {
        int userId = 1;
        int reservation = _repository.reservations(userId);
        Reserve reserve = _repository.upComing(userId);
        Restaurant restaurant = _repository.lastVisit(userId);
        StatisticResponse statisticResponse = new StatisticResponse(reserve, reservation, restaurant);
        BaseResponse<StatisticResponse> response = new BaseResponse<>(statisticResponse, "", true);
        return new RestResponseBuilder(200).withEntity(response).create();
    }


}
