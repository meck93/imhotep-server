package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String name, String username) {

        if (userRepository.findByName(name) != null) {
            throw new BadRequestHttpException("A user with the name: " + name + " already exists!");
        }
        if (userRepository.findByUsername(username) != null) {
            throw new BadRequestHttpException("A user with the username: " + username + " already exists!");
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(username);
        newUser.setStatus(UserStatus.ONLINE);
        newUser.setToken(UUID.randomUUID().toString());

        userRepository.save(newUser);

        log.debug("Created Information for User: {}", newUser);

        return newUser;
    }

    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId);

        if (user == null){
            throw new NotFoundException(userId, "User");
        }

        userRepository.delete(user);

        log.debug("Deleted User: {}", user);

        return "Deleted user with userId: " + userId;
    }

    /*
    This method deletes all the users in the userRepository and returns a list of all still existing users - should always be empty.
    Only implemented for testing purposes of the frontend.
     */
    public List<User> deleteAll(){
        userRepository.deleteAll();
        List<User> list = new ArrayList<>();
        userRepository.findAll().forEach(list::add);

        return list;
    }

    public List<User> listUsers() {
        log.debug("listUsers");

        List<User> result = new ArrayList<>();
        userRepository.findAll().forEach(result::add);

        if (result.isEmpty()) throw new NotFoundException("All Users");

        return result;
    }

    public User getUser(Long userId){
        log.debug("getUser: " + userId);

        User user = userRepository.findOne(userId);

        if (user == null) throw new NotFoundException(userId, "User");

        return user;
    }
}
