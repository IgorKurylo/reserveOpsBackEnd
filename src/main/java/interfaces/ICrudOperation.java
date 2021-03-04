package interfaces;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public interface ICrudOperation {


    Response create(Object object);

    Response update(Object object);

    Response delete();


    Response read();

    Response readList();

}
