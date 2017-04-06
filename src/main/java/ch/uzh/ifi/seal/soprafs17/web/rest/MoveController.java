package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.move.AMove;
import ch.uzh.ifi.seal.soprafs17.exceptions.BadRequestHttpException;
import ch.uzh.ifi.seal.soprafs17.service.move.MoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves/{moveId}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AMove getMove(@PathVariable("moveId") Long moveId) {
        return moveService.getMove(moveId);
    }

    @RequestMapping(value = CONTEXT + "/rounds/{roundId}/moves", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addMove(@RequestBody AMove move) {
        // Validating and applying the Move
        try {
            moveService.validateAndApply(move);
        }
        catch (BadRequestHttpException httpRequestException) {
            handleHttpStatusCodeExceptions(httpRequestException);
        }
        catch (Exception exception) {
            handleException(exception);
        }
    }
}