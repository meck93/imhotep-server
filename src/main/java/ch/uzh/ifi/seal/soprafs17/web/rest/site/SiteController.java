package ch.uzh.ifi.seal.soprafs17.web.rest.site;

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import ch.uzh.ifi.seal.soprafs17.service.card.MarketCardService;
import ch.uzh.ifi.seal.soprafs17.service.site.BuildingSiteService;
import ch.uzh.ifi.seal.soprafs17.service.site.MarketPlaceService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(SiteController.CONTEXT)
public class SiteController extends GenericController {

    // Standard URI Mapping of this class
    static final String CONTEXT = "games/{gameId}/sites";

    private final BuildingSiteService buildingSiteService;
    private final MarketPlaceService marketPlaceService;
    private final MarketCardService marketCardService;

    @Autowired
    public SiteController(BuildingSiteService buildingSiteService, MarketPlaceService marketPlaceService, MarketCardService marketCardService){
        this.buildingSiteService = buildingSiteService;
        this.marketPlaceService = marketPlaceService;
        this.marketCardService = marketCardService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{siteType}")
    @ResponseStatus(HttpStatus.OK)
    public BuildingSite getObelisk(@PathVariable("gameId") Long gameId, @PathVariable("siteType") String siteType) {
        return buildingSiteService.getBuildingSite(gameId, siteType);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/MARKET_PLACE")
    @ResponseStatus(HttpStatus.OK)
    public MarketPlace getObelisk(@PathVariable("gameId") Long gameId) {
        return marketPlaceService.getMarketPlace(gameId);
    }

    /*
     * Creates a Dummy-MarketCard on each Site of the Game for the Front-End Mapping/Modelling Purposes
     */
    @RequestMapping(method = RequestMethod.POST, value = "/dummyCard")
    @ResponseStatus(HttpStatus.CREATED)
    public String createDummyCard(@PathVariable("gameId") Long gameId, @RequestParam("color") String color, @RequestParam("marketCardType") MarketCardType marketCardType){
        MarketCard marketCard = this.marketCardService.createMarketCard(gameId, color, marketCardType);
        this.marketPlaceService.addDummyCard(gameId, marketCard);
        return "DummyData created!";
    }
}