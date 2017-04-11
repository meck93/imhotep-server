package ch.uzh.ifi.seal.soprafs17.entity.user;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class UserTest {

    @Test
    public void setId() {
        User testUser = new User();
        testUser.setId(1L);
        Assert.assertEquals(testUser.getId(), Long.valueOf(1L));
    }

    @Test
    public void setName() {
        User testUser = new User();
        testUser.setName("testName");
        Assert.assertEquals(testUser.getName(), "testName");
    }

    @Test
    public void setUsername() {
        User testUser = new User();
        testUser.setUsername("testUsername");
        Assert.assertEquals(testUser.getUsername(), "testUsername");
    }

    @Test
    public void setToken() {
        User testUser = new User();
        testUser.setToken("t123");
        Assert.assertEquals(testUser.getToken(),"t123");
    }

    @Test
    public void setStatus() {
        User testUser = new User();
        testUser.setStatus(UserStatus.ONLINE);
        Assert.assertEquals(testUser.getStatus(),UserStatus.ONLINE);
    }

    @Test
    public void setPlayer() {
        User testUser = new User();
        Player testPlayer = new Player();
        testUser.setPlayer(testPlayer);
        Assert.assertEquals(testUser.getPlayer(),testPlayer);
    }
}
