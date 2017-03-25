package ch.uzh.ifi.seal.soprafs17.web.rest;


import ch.uzh.ifi.seal.soprafs17.entity.MarketCard;
import ch.uzh.ifi.seal.soprafs17.service.MarketCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cristian on 25.03.2017.
 */

@RestController
@RequestMapping(MarketCardController.CONTEXT)
public class MarketCardController {
    Logger log  = LoggerFactory.getLogger(GameController.class);
    private MarketCardService marketCardService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "games";

    @Autowired
    public MarketCardController(MarketCardService marketCardService){
        this.marketCardService = marketCardService;
    }

    @RequestMapping(value="/MarketCard", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MarketCard triggerMarketCard() {
        return marketCardService.marketCardInfo();
    }
}
