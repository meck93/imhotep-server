package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.Application;
import ch.uzh.ifi.seal.soprafs17.GameConstants;
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
public class GetCardMoveTest {

    @Test
    public void GetCardMove() {
        GetCardMove testMove = new GetCardMove();
        testMove.setMoveType(GameConstants.GET_CARD);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getMoveType(), GameConstants.GET_CARD);
    }

    @Test
    public void setMarketCardId() {
        GetCardMove testMove = new GetCardMove();
        testMove.setMarketCardId(1L);
        Assert.assertNotNull(testMove.getMarketCardId());
        Assert.assertEquals(testMove.getMarketCardId(), Long.valueOf(1L));
    }
}
