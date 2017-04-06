package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MoveController extends GenericController {

    Logger log = LoggerFactory.getLogger(MoveController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games/{gameId}";

    private MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @RequestMapping(value = CONTEXT + "/moves", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AMove> listMoves(@PathVariable("gameId") Long gameId) {
        return moveService.getGameMoves(gameId);
    }

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AMove> listMoves(@PathVariable("gameId") Long gameId, @PathVariable("roundNr") int roundNr) {
        return moveService.getRoundMoves(gameId, roundNr);
    }

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves/{moveId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AMove getMove(@PathVariable("moveId") Long moveId) {
        return moveService.getMove(moveId);
    }

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public AMove addMove(@RequestBody AMove move) {
        // TODO: MUST THROW EXCEPTION!
        return moveService.addMove(move);
    }
}