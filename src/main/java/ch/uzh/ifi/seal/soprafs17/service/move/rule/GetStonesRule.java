package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.GetStonesMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
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

        // Index of the Player to be removed
        int playerIndex = 0;
        // Setting the index to the correct element in the list
        for (Player player : players){
            if (player.getPlayerNumber() == move.getPlayerNr()){
                playerIndex = players.indexOf(player);
            }
        }
        // removing the current Player from the List of all Players
        Player player = players.remove(playerIndex);

        // Retrieving the SupplySled and the StoneQuarry of the current Player
        SupplySled supplySled = player.getSupplySled();
        StoneQuarry stoneQuarry = game.getStoneQuarry();

        // Calculating the number of Stones from the StoneQuarry to be moved to the SupplySled
        int stonesOnSupplySled = player.getSupplySled().getStones().size();
        int nrOfNewStones = GameConstants.MAX_STONES_SUPPLY_SLED - stonesOnSupplySled;

        // The current Stones on the SupplySled
        List<Stone> newSupplySledStones = supplySled.getStones();

        // Adding at most 3 stones to the SupplySled
        for (int i = 0; i < nrOfNewStones && i != GameConstants.MAX_STONES_ADDED_PER_MOVE; i++) {
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
