package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.*;
import ch.uzh.ifi.seal.soprafs17.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GameController.CONTEXT)
public class GameController extends GenericController {

    Logger log  = LoggerFactory.getLogger(GameController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games";

    private GameService gameService;
    private PlayerService playerService;
    private MarketCardService marketCardService;
    private RoundCardService roundCardService;
    private RoundService roundService;
    private MarketPlaceService marketPlaceService;
    private ShipService shipService;
    private StoneService stoneService;


    @Autowired
    public GameController(GameService gameService, PlayerService playerService,
                          MarketCardService marketCardService, RoundCardService roundCardService,
                          RoundService roundService, MarketPlaceService marketPlaceService,
                          ShipService shipService, StoneService stoneService){
        this.gameService = gameService;
        this.playerService = playerService;
        this.marketCardService = marketCardService;
        this.roundCardService = roundCardService;
        this.roundService = roundService;
        this.marketPlaceService = marketPlaceService;
        this.shipService = shipService;
        this.stoneService = stoneService;
    }

    // TODO Correct the implementation: Controller calls the service to do a action
    // TODO Correct the implementation: Service handles the request in service

    /*
     * Context: /game
     * Returns a list of all games
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> listGames() {
        return gameService.listGames();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    // TODO Rename addGame in both Controller and Service to createGame
    public String addGame(@RequestBody Game game, @RequestParam("token") String userToken) {
        return gameService.addGame(game, userToken);
    }

    @RequestMapping(value = "/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long gameId) {
        return gameService.getGame(gameId);
    }

    @RequestMapping(value = "/{gameId}/start", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        gameService.startGame(gameId, userToken);
    }

    @RequestMapping(value = "/{gameId}/stop", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void stopGame(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        gameService.stopGame(gameId, userToken);
    }

    // /MarketCard
    @RequestMapping(value="/MarketCard", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MarketCard triggerMarketCard() {
        return marketCardService.marketCardInfo();
    }

    // /RoundCard
    @RequestMapping(value="/RoundCard", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public RoundCard triggerRoundCard() {
        return roundCardService.roundCardInfo();
    }

    // /Round
    @RequestMapping(value="/Round", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Round triggerRound() {
        return roundService.testRound();
    }

    // /MarketPlace
    @RequestMapping(value="/MarketPlace", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public MarketPlace triggerMarketPlace() {
        return marketPlaceService.marketPlaceInfo();
    }

    // /Ship
    @RequestMapping(value="/Ship", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Ship triggerShip() {
        return shipService.shipInfo();
    }

    // /Stone (FOR TESTING PURPOSES ONLY)
    @RequestMapping(value="/Stone", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Stone triggerStone() {
        return stoneService.stoneInfo();
    }

}