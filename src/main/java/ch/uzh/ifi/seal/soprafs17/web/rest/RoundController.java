package ch.uzh.ifi.seal.soprafs17.web.rest;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Round;
import ch.uzh.ifi.seal.soprafs17.service.RoundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RoundController.CONTEXT)
public class RoundController {
    Logger log  = LoggerFactory.getLogger(GameController.class);
    private RoundService roundService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "games";

    @Autowired
    public RoundController(RoundService roundService){
        this.roundService = roundService;
    }

    @RequestMapping(value="/Round", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Round triggerRound() {
        return roundService.testRound();
    }

}
