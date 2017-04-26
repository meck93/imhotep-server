package ch.uzh.ifi.seal.soprafs17.web.rest.game;

import ch.uzh.ifi.seal.soprafs17.entity.game.Round;
import ch.uzh.ifi.seal.soprafs17.entity.game.Ship;
import ch.uzh.ifi.seal.soprafs17.service.game.RoundService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RoundController.CONTEXT)
public class RoundController extends GenericController {

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games/{gameId}/rounds";

    private final RoundService roundService;

    @Autowired
    public RoundController(RoundService roundService) {
        this.roundService = roundService;
    }

    /*
     * Returns a list of all Rounds in Game: {GameId}
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Round> listRounds(@PathVariable("gameId") Long gameId) {
        return roundService.listRounds(gameId);
    }

    /*
     * Returns the Round with RoundNr in Game with GameID
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{roundNr}")
    @ResponseStatus(HttpStatus.OK)
    public Round getRound(@PathVariable("gameId") Long gameId, @PathVariable("roundNr") int roundNumber) {
        return roundService.getRoundByNr(gameId, roundNumber);
    }

    /*
     * Returns all ships of a specified Round
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{roundNr}/ships")
    @ResponseStatus(HttpStatus.OK)
    public List<Ship> getShips(@PathVariable("gameId") Long gameId, @PathVariable("roundNr") int roundNumber) {
        return roundService.getShips(gameId, roundNumber);
    }

    /*
     * Returns the ship with Id: {shipId}
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{roundNr}/ships/{shipId}")
    @ResponseStatus(HttpStatus.OK)
    public Ship getShip(@PathVariable("shipId") Long shipId) {
        return roundService.getShip(shipId);
    }
}