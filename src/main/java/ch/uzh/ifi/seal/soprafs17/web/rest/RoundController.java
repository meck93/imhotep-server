package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.Round;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
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
     * Handles the request for the game with Id: {gameId}
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{roundNr}")
    @ResponseStatus(HttpStatus.OK)
    public Round getRound(@PathVariable("gameId") Long gameId, @PathVariable("roundNr") int roundNumber) {
        return roundService.getRoundByNr(gameId, roundNumber);
    }
}