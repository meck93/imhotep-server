package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.constant.BuildingSiteType;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.service.BuildingSiteService;
import ch.uzh.ifi.seal.soprafs17.service.MarketPlaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SiteController.CONTEXT)
public class SiteController extends GenericController {

    Logger log  = LoggerFactory.getLogger(SiteController.class);
    private final BuildingSiteService buildingSiteService;
    private final MarketPlaceService marketPlaceService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "games/{gameId}/sites";

    @Autowired
    public SiteController(BuildingSiteService buildingSiteService, MarketPlaceService marketPlaceService){
        this.buildingSiteService = buildingSiteService;
        this.marketPlaceService = marketPlaceService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{buildingSiteType}")
    @ResponseStatus(HttpStatus.OK)
    public BuildingSite getObelisk(@PathVariable("gameId") Long gameId, @PathVariable("buildingSiteType") BuildingSiteType buildingSiteType) {
        return buildingSiteService.getBuildingSite(gameId, buildingSiteType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/MARKET_PLACE")
    @ResponseStatus(HttpStatus.OK)
    public MarketPlace getObelisk(@PathVariable("gameId") Long gameId) {
        return marketPlaceService.getMarketPlace(gameId);
    }
    /*
     * Creates a Dummy-Stone on each Site of the Game for the Front-End Mapping/Modelling Purposes
     */
    @RequestMapping(method = RequestMethod.POST, value = "/dummy")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDummyData(@PathVariable("gameId") Long gameId){
        buildingSiteService.createDummyData(gameId);
        return "DummyData created!";
    }
}