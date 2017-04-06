package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 06.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.constant.MoveType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@Transactional
public class MoveTest {

    @Test
    public void setId() {
        Move testMove = new Move();
        testMove.setId(1L);
        Assert.assertEquals(testMove.getId(), Long.valueOf(1L));
    }

    @Test
    public void setName() {
        Move testMove = new Move();
        testMove.setName("testMove");
        Assert.assertEquals(testMove.getName(),"testMove");
    }

    @Test
    public void setMoveType() {
        Move testMove = new Move();
        testMove.setMoveType(MoveType.NEW_STONE);
        Assert.assertEquals(testMove.getMoveType(),MoveType.NEW_STONE);
    }
}
