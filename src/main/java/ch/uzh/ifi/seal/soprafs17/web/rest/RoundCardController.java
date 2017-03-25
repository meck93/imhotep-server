package ch.uzh.ifi.seal.soprafs17.web.rest;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.service.RoundCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RoundCardController.CONTEXT)
public class RoundCardController {

    Logger log  = LoggerFactory.getLogger(GameController.class);
    private RoundCardService roundCardService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "games";

    @Autowired
    public RoundCardController(RoundCardService roundCardService){
        this.roundCardService = roundCardService;
    }
}
