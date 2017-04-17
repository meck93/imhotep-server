package ch.uzh.ifi.seal.soprafs17.service.move.rule.card;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.IRule;

import java.util.List;

public class HammerRule implements IRule {
    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.HAMMER.toString());
    }

    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        PlayCardMove newMove = (PlayCardMove) move;

        // Retrieving the Player
        Player player = game.getPlayerByPlayerNr(game.getCurrentPlayer());

        // Removing the marketCard from the players deck of cards
        player.getHandCards().remove(player.getMarketCardById(newMove.getCardId()));

        // removing the current Player from the List of all Players
        game.getPlayers().remove(player);

        /* PART ONE: GET STONES */

        // Retrieving the SupplySled and the StoneQuarry of the current Player
        SupplySled supplySled = player.getSupplySled();
        StoneQuarry stoneQuarry = game.getStoneQuarry();

        // Calculating the number of Stones from the StoneQuarry to be moved to the SupplySled
        int stonesOnSupplySled = player.getSupplySled().getStones().size();
        int nrOfNewStones = GameConstants.MAX_STONES_SUPPLY_SLED - stonesOnSupplySled;

        // The current Stones on the SupplySled
        List<Stone> updatedStones = supplySled.getStones ();

        // Adding at most 3 stones to the SupplySled
        for (int i = 0; i < nrOfNewStones && i != GameConstants.MAX_STONES_ADDED_PER_MOVE; i++) {
            Stone stone = stoneQuarry.getStonesByPlayerNr(player.getPlayerNumber()).remove(0);
            updatedStones.add(stone);
        }

        // Adding the updated StoneQuarry to the Game
        game.setStoneQuarry(stoneQuarry);


        /* PART TWO: PLACE STONE */

        // removing one stone from players' sled
        Stone stone = updatedStones.remove(0);

        // Adding the position to selected stone
        stone.setPlaceOnShip(newMove.getPlaceOnShip());

        // Find the assigned ship
        Ship assignedShip = game.getRoundByRoundCounter().getShipById(newMove.getShipId());

        // Remove the assigned ship
        game.getRoundByRoundCounter().getShips().remove(assignedShip);

        // Adding stone on ship
        assignedShip.getStones().add(stone);

        // Adding the updated sled to the game
        supplySled.setStones(updatedStones);

        // Adding the updated ship back to the game
        game.getRoundByRoundCounter().getShips().add(assignedShip);

        // Adding the updated SupplySled to the Player
        player.setSupplySled(supplySled);

        // Adding the updated Player back to the Game
        game.getPlayers().add(player);

        return game;
    }
}
