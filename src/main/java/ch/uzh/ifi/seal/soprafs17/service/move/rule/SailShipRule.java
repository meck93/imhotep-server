package ch.uzh.ifi.seal.soprafs17.service.move.rule;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.entity.move.SailShipMove;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.exceptions.ApplyMoveException;

import java.util.List;

/**
 * Created by User on 14.04.2017.
 */
public class SailShipRule implements IRule {
    @Override
    public boolean supports(AMove move) {
        return move instanceof SailShipMove;
    }

    /*
     * Apply the changes to the game in regards to the Place_Stone move
     */
    @Override
    public Game apply(AMove move, Game game) throws ApplyMoveException {

        // Typecasting the AbstractMove to a PlaceStoneMove
        SailShipMove newMove = (SailShipMove) move;

        // Updating the ship
        game.getRoundByRoundCounter(game.getRoundCounter()).getShipById(newMove.getShipId()).setHasSailed(true);

        //Updating the status of the game
        if(game.getSiteById(newMove.getTargetSiteId()).getClass().equals(new MarketPlace().getClass())){
            game.setStatus(GameStatus.SUBROUND);
            game.getMarketPlace().setDocked(true);
        }
        else{
            // List of all current players
            List<BuildingSite> buildingSites = game.getBuildingSites();

            // Index of the Player to be removed
            int siteIndex = 0;
            // Setting the index to the correct element in the list
            for (BuildingSite site : buildingSites){
                if (site.getId() == newMove.getTargetSiteId()){
                    siteIndex = buildingSites.indexOf(site);
                }
            }
            // removing the current Player from the List of all Players
            BuildingSite site = buildingSites.remove(siteIndex);

            // Setting the site docked
            site.setDocked(true);

            //Adding the site back to the game
            buildingSites.add(site);
            game.setBuildingSites(buildingSites);
        }
        return game;
    }
}
