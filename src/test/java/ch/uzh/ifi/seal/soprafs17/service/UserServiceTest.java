package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;


/**
 * Test class for the UserResource REST resource.
 *
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
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
        User user = userService.createUser("testName", "testUsername");
        user.setToken("t123");
        assertNotNull(userRepository.findByToken("t123"));
        Assert.assertEquals(userRepository.findByToken("t123"), user);
    }

    @Test
    public void deleteUser() {
        User user = userService.createUser("testName", "testUsername");
        user.setToken("t123");
        userRepository.delete(user.getId());
        Assert.assertNull(userRepository.findById(user.getId()));
    }


}
