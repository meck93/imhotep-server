package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

import java.util.List;

public class GetStonesRule implements IRule {

    @Override
    public boolean supports(AMove move) {
        return move instanceof GetStonesMove;
    }
    /*
     * Apply the changes to the game in regards to the Get_Stone Move
     */
    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {
        // List of all current players
        List<Player> players = game.getPlayers();
        // The current player
        Player player = players.remove(move.getPlayerNr());
        // The SupplySled and the StoneQuarry of the current Player
        SupplySled supplySled = player.getSupplySled();
        StoneQuarry stoneQuarry = game.getStoneQuarry();

        // Calculating the nr of Stones from the StoneQuarry to be moved to the SupplySled
        int stonesOnSupplySled = (int) player.getSupplySled().getStones().size();
        int nrOfNewStones = GameConstants.MAX_STONES_SUPPLY_SLED - stonesOnSupplySled;

        // The current Stones on the SupplySled
        List<Stone> newSupplySledStones = supplySled.getStones();

        // Adding the required amount of Stones to the SupplySled
        for (int i = 0; i < nrOfNewStones; i++) {
            Stone stone = stoneQuarry.getStonesByPlayerNr(player.getPlayerNumber()).remove(0);
            newSupplySledStones.add(stone);
        }

        // Setting the new List of Stones to the SupplySled
        supplySled.setStones(newSupplySledStones);
        // Adding the updated SupplySled to the Player
        player.setSupplySled(supplySled);
        // Adding the updated StoneQuarry to the Game
        game.setStoneQuarry(stoneQuarry);
        // Adding the updated Player back to the Game
        players.add(player);
        game.setPlayers(players);

        return game;
    }
}
