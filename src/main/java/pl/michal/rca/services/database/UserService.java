package pl.michal.rca.services.database;

import org.mindrot.jbcrypt.BCrypt;
import pl.michal.rca.models.User;
import pl.michal.rca.repositories.UserRepository;

public class UserService implements pl.michal.rca.services.interfaces.UserService {
    private UserRepository userRepository = new UserRepository();
    @Override
    public Boolean verifyLogin(User user) {
        User userFromDB = userRepository.read(user.getUsername());
        return BCrypt.checkpw(user.getPassword(), userFromDB.getPassword());
    }

    @Override
    public User read(User user) {
        User userFromDB = userRepository.read(user.getUsername());
        return userFromDB;
    }

    @Override
    public User readById(int userId) {
        return userRepository.readById(userId);
    }
}
