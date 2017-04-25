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

    /*
     * Handles the request for the size of the StoneQuarry of each Player
     */
    @RequestMapping(value = "/{gameId}/stoneQuarry/{playerNumber}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public int sizeOfQuarry(@PathVariable Long gameId, @PathVariable int playerNumber) {
        return gameService.sizeOfQuarry(gameId, playerNumber);
    }
}
