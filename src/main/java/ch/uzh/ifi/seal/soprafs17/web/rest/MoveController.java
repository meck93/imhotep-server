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
    static final String CONTEXT = "/games/{gameId}/players/{playerNr}/moves";

    private MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @RequestMapping(value = CONTEXT, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AMove> listMoves(@PathVariable("gameId") Long gameId, @PathVariable("playerNr") Long playerNr) {
        //TODO getMoves must be implemented in the MoveService
        return moveService.getMoves(gameId, playerNr);
    }

    @RequestMapping(value = CONTEXT + "/{moveNr}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AMove getMove(@PathVariable("gameId") Long gameId, @PathVariable("playerNr") Long playerNr, @PathVariable Long moveId) {
        // TODO Execution of getMove in moveService
        return moveService.getMove(moveId);
    }

    @RequestMapping(value = "/games/{gameId}/rounds/{roundId}/moves", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public AMove addMove(@RequestParam String moveType, @PathVariable("gameId") Long gameId, @PathVariable("roundId") Long roundId) {
        return moveService.addMove(moveType, gameId, roundId);
    }
}