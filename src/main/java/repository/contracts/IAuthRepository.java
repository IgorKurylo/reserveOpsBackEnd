package repository.contracts;

import models.user.AuthCredentials;
import models.user.User;

public interface IAuthRepository {

    User authentication(AuthCredentials authCredentials);

    int registration(User user);
}
