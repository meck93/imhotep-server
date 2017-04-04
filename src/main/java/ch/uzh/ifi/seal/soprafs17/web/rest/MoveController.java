package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.service.MoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(MoveController.CONTEXT)
public class MoveController extends GenericController {

    Logger log = LoggerFactory.getLogger(MoveController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games/{gameId}/players/{playerNr}/moves";

    private MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable("gameId") Long gameId, @PathVariable("playerNr") Long playerNr) {
        //TODO getMoves must be implemented in the MoveService
        return moveService.getMoves(gameId, playerNr);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody Move move, @PathVariable("gameId") Long gameId) {
        // TODO Execution of addMove in moveService
        moveService.addMove(move);
    }

    @RequestMapping(value = "/{moveNr}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Move getMove(@PathVariable("gameId") Long gameId, @PathVariable("playerNr") Long playerNr, @PathVariable Long moveId) {
        // TODO Execution of getMove in moveService
        return moveService.getMove(moveId);
    }
}