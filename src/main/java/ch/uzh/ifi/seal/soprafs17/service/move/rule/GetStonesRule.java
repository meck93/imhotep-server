package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

public class GetStonesRule implements IRule {

    @Override
    public boolean supports(AMove move) {
        return move instanceof GetStonesMove;
    }

    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {
        // Test if the Move can be applied
        if(!true){
            String msg = "Applying Move: " + move + " failed";
            throw new ApplyMoveException(msg);
        } else {
            // DO THE APPLY GAME LOGIC
            // game.doSomething()
        }
        return game;
    }
}
