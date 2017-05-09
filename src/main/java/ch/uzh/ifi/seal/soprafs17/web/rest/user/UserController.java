package ch.uzh.ifi.seal.soprafs17.web.rest.user;

import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.service.user.UserService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(UserController.CONTEXT)
public class UserController extends GenericController {

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
        return userService.createUser(user.getUsername());
    }

    @RequestMapping(method = RequestMethod.GET, value = "{userId}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long userId) {
         return userService.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{userId}/logout")
    @ResponseStatus(HttpStatus.OK)
    public String removeUser(@PathVariable Long userId) {
        return userService.deleteUser(userId);
    }

    /*
     * This method handles the request to delete all users. Only implemented for testing purposes of the frontend.
     */
    @RequestMapping(method = RequestMethod.POST, value = "deleteAll")
    @ResponseStatus(HttpStatus.OK)
    public List<User> deleteAll(){
        return userService.deleteAll();
    }
}
