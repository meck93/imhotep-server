package ch.uzh.ifi.seal.soprafs17.entity.move;

/**
 * Created by Cristian on 20.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.Application;
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
public class GetStonesMoveTest {

    @Test
    public void GetStonesMove() {
        GetStonesMove testMove = new GetStonesMove("TEST");
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getMoveType(),"TEST");
    }
}
