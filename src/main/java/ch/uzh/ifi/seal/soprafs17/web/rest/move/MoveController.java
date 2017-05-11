package ch.uzh.ifi.seal.soprafs17.web.rest.move;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.service.move.MoveService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class MoveController extends GenericController {

    // Standard URI Mapping of this class
    private static final String CONTEXT = "/games/{gameId}";

    private MoveService moveService;

    @Autowired
    public MoveController(MoveService moveService) {
        this.moveService = moveService;
    }

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody AMove move) {
        // Validating and applying the Move
        this.moveService.validateAndApply(move);
    }

    @RequestMapping(value = CONTEXT + "/moves", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<AMove> getMoveLog(@PathVariable("gameId") Long gameId, @RequestParam("numberOfMoves") int numberOfMoves) {
        // Returning the log for the required amount of Moves
        return this.moveService.findLastMoves(gameId, numberOfMoves);
    }


}