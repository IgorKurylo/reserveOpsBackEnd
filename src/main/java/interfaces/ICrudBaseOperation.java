package interfaces;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public interface ICrudBaseOperation<E> {


    Response create(E object, String headers);

    Response update(E object, String headers);

    Response delete(int id,String headers);


    Response read(int id, String headers);

    Response readList(String headers);


}
