package ch.uzh.ifi.seal.soprafs17.service.move.rule.card;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.PlayCardMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;
import ch.uzh.ifi.seal.soprafs17.service.move.rule.IRule;

/**
 * Created by User on 19.04.2017.
 */
public class LeverRule implements IRule{

    @Override
    public boolean supports(AMove move) {
        return move.getMoveType().equals(MarketCardType.LEVER.toString());
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

        // Find the assigned ship
        Ship assignedShip = game.getRoundByRoundCounter().getShipById(newMove.getShipId());

        // Updating the ship
        assignedShip.setHasSailed(true);

        //Updating the status of the game
        if(game.getSiteById(newMove.getTargetSiteId()).getClass().equals(MarketPlace.class)){
            // Setting the Status to SUBROUND & docking the ship
            game.setStatus(GameStatus.SUBROUND);
            game.getMarketPlace().setDocked(true);
        }
        else {
            // Retrieving the correct BuildingSite
            BuildingSite buildingSite = (BuildingSite) game.getSiteById(newMove.getTargetSiteId());

            // Setting the site docked
            buildingSite.setDocked(true);

            // Unloading the Stones from the Ship onto the BuildingSite
            for (int i = 0; i < newMove.getUnloadingOrder().size(); i++){
                // Retrieving the correct Stone in order of the placement
                Stone stone2 = assignedShip.getStoneById(newMove.getUnloadingOrder().get(i));
                // When Stone == Null, there was no stone on this position on the ship
                if (stone2 == null){
                    continue;
                }
                // Adding the Stone to the Site
                buildingSite.getStones().add(stone2);
                // Removing the Stone from the Ship
                assignedShip.getStones().remove(stone2);
            }
        }

        // Adding the updated Player back to the Game
        game.getPlayers().add(player);

        return game;
    }
}