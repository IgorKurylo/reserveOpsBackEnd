package repository.contracts;

import models.AccessToken;
import models.AuthCredentials;
import models.BaseResponse;
import models.User;
import org.jvnet.hk2.annotations.Contract;

import java.util.Optional;

public interface IAuthRepository {

    User authentication(AuthCredentials authCredentials);
}
