package interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ICrudBaseOperation<E> {


    Response create(E object);

    Response update(E object);

    Response delete();


    Response read(int id);

    Response readList();


}
