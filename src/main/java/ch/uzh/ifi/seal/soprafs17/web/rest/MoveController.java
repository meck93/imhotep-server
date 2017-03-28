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
    static final String CONTEXT = "/games/{gameId}/players/{playersId}/moves";

    private MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    // TODO Correct the implementation: Controller calls the service to do a action
    // TODO Correct the implemenation: Service handles the request in service

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Move> listMoves(@PathVariable Long gameId, @PathVariable Long playerId) {
        //TODO getMoves must be implemented in the MoveService
        return moveService.getMoves(gameId, playerId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody Move move) {
        // TODO Execution of addMove in moveService
        moveService.addMove(move);
    }

    @RequestMapping(value = "/{moveId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Move getMove(@PathVariable Long gameId, @PathVariable Long moveId) {
        // TODO Execution of getMove in moveService
        return moveService.getMove(moveId);
    }
}