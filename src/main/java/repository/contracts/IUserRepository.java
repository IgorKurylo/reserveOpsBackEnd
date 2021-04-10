package repository.contracts;

import models.Restaurant;

public interface IUserRepository {
    Restaurant adminMetaData(int userId);
}
