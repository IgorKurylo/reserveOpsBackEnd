package repository.contracts;

import models.AccessToken;
import models.BaseResponse;
import models.User;
import org.jvnet.hk2.annotations.Contract;

public interface IAuthRepository {

    User authentication(String username, String password);
}
