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
public class AMoveTest {

    @Test
    public void AMove() {
        AMove testMove = new PlaceStoneMove("TEST");
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getMoveType(),"TEST");
    }

    @Test
    public void setDescription() {
        AMove testMove = new GetStonesMove();
        String testDescrption = "Hello World";

        testMove.setId(1L);
        testMove.setDescription(testDescrption);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getDescription(), testDescrption);
    }

    @Test
    public void setId() {
        AMove testMove = new GetStonesMove();
        testMove.setId(1L);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getId(),Long.valueOf(1L));
    }

    @Test
    public void setMoveType() {
        AMove testMove = new GetStonesMove();
        testMove.setMoveType(GameConstants.GET_STONES);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getMoveType(),GameConstants.GET_STONES);
    }

    @Test
    public void setGameId() {
        AMove testMove = new GetStonesMove();
        testMove.setGameId(1L);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getGameId(),Long.valueOf(1L));
    }

    @Test
    public void setPlayerNr() {
        AMove testMove = new GetStonesMove();
        testMove.setPlayerNr(1);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getPlayerNr(),1);
    }

    @Test
    public void setRoundNr() {
        AMove testMove = new GetStonesMove();
        testMove.setRoundNr(1);
        Assert.assertNotNull(testMove);
        Assert.assertEquals(testMove.getRoundNr(),1);
    }
}