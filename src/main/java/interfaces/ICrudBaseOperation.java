package interfaces;

import models.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public interface ICrudBaseOperation<E> {


    Response create(E object);

    Response update(E object);

    Response delete();


    Response read(int id);

    Response readList();

}
