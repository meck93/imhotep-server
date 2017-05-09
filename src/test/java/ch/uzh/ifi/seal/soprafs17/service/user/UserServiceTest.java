package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
        Assert.assertNull(userRepository.findByToken("t123"));
        User user = userService.createUser("testUsername");
        user.setToken("t123");
        Assert.assertNotNull(userRepository.findByToken("t123"));
        Assert.assertEquals(userRepository.findByToken("t123"), user);
    }

    @Test
    public void deleteUser() {
        User user = userService.createUser("testUsername");
        Assert.assertNotNull(userRepository.findById(1L));
        user.setToken("t123");
        userService.deleteUser(1L);
        Assert.assertNull(userRepository.findById(1L));
    }

    @Test
    public void deleteAll() {
        User user1 = userService.createUser("testUsername1");
        User user2 = userService.createUser("testUsername2");
        user1.setToken("t123");
        user2.setToken("t1234");
        Assert.assertEquals(userRepository.findByToken("t123"), user1);
        Assert.assertEquals(userRepository.findByToken("t1234"), user2);
        userService.deleteAll();
        Assert.assertNull(userRepository.findByToken("t123"));
        Assert.assertNull(userRepository.findByToken("t1234"));
    }

    @Test
    public void listUsers() {
        try {
            List<User> testUsers = userService.listUsers();
        } catch(NotFoundException e) {}

        User user1 = userService.createUser("testUsername1");
        User user2 = userService.createUser("testUsername2");
        user1.setToken("t123");
        user2.setToken("t1234");
        List<User> testUsers = userService.listUsers();
        Assert.assertNotNull(testUsers);
        Assert.assertEquals(testUsers.get(0),user1);
        Assert.assertEquals(testUsers.get(1),user2);
    }

    @Test
    public void getUser() {
        try {
            userService.getUser(1L);
        } catch(NotFoundException e) {}

        User user1 = userService.createUser("testUsername1");
        User user2 = userService.createUser("testUsername2");
        user1.setToken("t123");
        user2.setToken("t1234");
        Assert.assertEquals(userService.getUser(1L),user1);
        Assert.assertEquals(userService.getUser(2L),user2);
    }
}
