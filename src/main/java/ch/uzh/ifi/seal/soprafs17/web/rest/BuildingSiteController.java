package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.constant.SiteType;
import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;
import ch.uzh.ifi.seal.soprafs17.service.BuildingSiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController(value = BuildingSiteController.CONTEXT)
@RequestMapping(BuildingSiteController.CONTEXT)
public class BuildingSiteController {

    Logger log  = LoggerFactory.getLogger(GameController.class);
    private final BuildingSiteService buildingSiteService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "games/{gameId}";

    @Autowired
    public BuildingSiteController(BuildingSiteService buildingSiteService){
        this.buildingSiteService = buildingSiteService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{siteType}")
    @ResponseStatus(HttpStatus.OK)
    public BuildingSite getObelisk(@PathVariable("gameId") Long gameId, @PathVariable("siteType") SiteType siteType) {
        return buildingSiteService.getBuildingSite(gameId, siteType);
    }

    /*
     * HttpStatus Exceptions
     */
    @ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="Don't be stupid. written by daif")
    public class OrderNotFoundException extends RuntimeException {
    }
}