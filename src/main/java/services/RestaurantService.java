package services;

import interfaces.ICrudOperation;
import models.Restaurant;

import javax.ws.rs.core.Response;

public class RestaurantService implements ICrudOperation<Restaurant>
{
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
}
