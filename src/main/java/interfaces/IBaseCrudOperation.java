package interfaces;

import javax.ws.rs.core.Response;
import java.util.List;

public interface IBaseCrudOperation {

    Response create();

    Response update();

    Response delete();

    Response read();

    Response readList();

}
