package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
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

    @Autowired
    public GameController(GameService gameService){
        this.gameService = gameService;
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

    /*
     * Is this function necessary? -> The games are created in the Lobby
     */
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
    /*
     * This request deletes a game entity. Only exists for the frontend for testing purposes.
     * @Param
     */
    @RequestMapping(value = "/{gameId}/delete", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable Long gameId) {
        gameService.deleteGame(gameId);
    }
}