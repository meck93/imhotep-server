package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;


@RestController
@RequestMapping(UserController.CONTEXT)
public class UserController extends GenericController {

    Logger log  = LoggerFactory.getLogger(UserController.class);

    // Standard URI mapping of this class
    static final String CONTEXT = "/users";

    // Service associated with this controller
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> listUsers() {
        return userService.listUsers();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User addUser(@RequestBody User user) {
        return userService.createUser(user.getName(), user.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long userId) {
         return userService.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/login")
    @ResponseStatus(HttpStatus.OK)
    public User login(@PathVariable Long userId) {
        return userService.login(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@PathVariable Long userId, @RequestParam("token") String userToken) {
        userService.logout(userId, userToken);
    }
    /*
    This method handles the request to delete all users. Only implemented for testing purposes of the frontend.
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteAll")
    @ResponseStatus(HttpStatus.OK)
    public List<User> deleteAll(){
        return userService.deleteAll();
    }
}
