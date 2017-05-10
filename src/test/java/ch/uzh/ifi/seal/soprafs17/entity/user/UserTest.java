package ch.uzh.ifi.seal.soprafs17.entity.user;

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.UserStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class UserTest {

    @Test
    public void setId() {
        User testUser = new User();
        testUser.setId(1L);
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getId(), Long.valueOf(1L));
    }

    @Test
    public void setUsername() {
        User testUser = new User();
        testUser.setUsername("testUsername");
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getUsername(), "testUsername");
    }

    @Test
    public void setToken() {
        User testUser = new User();
        testUser.setToken("t123");
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getToken(),"t123");
    }

    @Test
    public void setStatus() {
        User testUser = new User();
        testUser.setStatus(UserStatus.ONLINE);
        Assert.assertNotNull(testUser);
        Assert.assertEquals(testUser.getStatus(),UserStatus.ONLINE);
    }

    @Test
    public void setPlayer() {
        User testUser = new User();
        Player testPlayer = new Player();
        testUser.setPlayer(testPlayer);
        Assert.assertNotNull(testUser);
        Assert.assertNotNull(testPlayer);
        Assert.assertEquals(testUser.getPlayer(),testPlayer);
    }
}
