package ch.uzh.ifi.seal.soprafs17.web.rest.game;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.web.rest.GenericController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(GameController.CONTEXT)
public class GameController extends GenericController {

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games";
    
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

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
     * Handles the request for the game with Id: {gameId}
     */
    @RequestMapping(value = "/{gameId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Game getGame(@PathVariable Long gameId) {
        return gameService.findById(gameId);
    }

    @RequestMapping(value = "/{gameId}/start", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void startGame(@PathVariable Long gameId, @RequestParam("playerId") Long playerId) {
        gameService.startGame(gameId, playerId);
    }

    @RequestMapping(value = "/{gameId}/stop", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void stopGame(@PathVariable Long gameId, @RequestParam("playerId") Long playerId) {
        gameService.stopGame(gameId, playerId);
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
