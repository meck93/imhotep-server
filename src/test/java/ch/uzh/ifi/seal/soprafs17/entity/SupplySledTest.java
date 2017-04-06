package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class SupplySledTest {

    @Test
    public void setId() {
        SupplySled testSupplySled = new SupplySled();
        testSupplySled.setId(1L);
        Assert.assertEquals(testSupplySled.getId(), Long.valueOf(1L));
    }

    @Test
    public void setStones() {
        SupplySled testSupplySled = new SupplySled();
        List<Stone> testStones = new ArrayList<>();
        testSupplySled.setStones(testStones);
        Assert.assertEquals(testSupplySled.getStones(),testStones);
    }

    @Test
    public void setPlayer() {
        SupplySled testSupplySled = new SupplySled();
        Player testPlayer = new Player();
        testSupplySled.setPlayer(testPlayer);
        Assert.assertEquals(testSupplySled.getPlayer(),testPlayer);
    }
}
