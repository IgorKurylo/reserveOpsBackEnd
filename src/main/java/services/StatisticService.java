package services;

import repository.contracts.IStatisticRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("dashboard")
public class StatisticService {

    @Inject
    IStatisticRepository _repository;

    @GET
    @Path("upcoming")
    public Response upcomingReserve() {
        return null;
    }

    @GET()
    @Path("reservations")
    public Response reservations() {
        return null;
    }

    @GET()
    @Path("lastvisit")
    public Response lastVisit() {
        return null;
    }


}
