package pl.michal.rca.services.interfaces;

import pl.michal.rca.models.User;

public interface UserService {
    Boolean verifyLogin(User user);

    User read(User user);
    User readById(int userId);
}
