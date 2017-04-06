package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.service.LobbyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(LobbyController.CONTEXT)
public class LobbyController extends GenericController {

    Logger log  = LoggerFactory.getLogger(LobbyController.class);
    private LobbyService lobbyService;

    // Standard URI Mapping of this class
    static final String CONTEXT = "/lobby";

    @Autowired
    public LobbyController(LobbyService lobbyService){
        this.lobbyService = lobbyService;
    }

    /*
     * Context: /lobby
     * Returns a list of all games
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Game> listGames() {
        return lobbyService.listGames();
    }

    /*
     * Context: /lobby/games
     * Creates a game
     * @Param Game a game body (at least all non-nullable fields), userId - User
     */
    @RequestMapping(method = RequestMethod.POST, value = "games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Game createGame(@RequestBody Game game, @RequestParam Long userId){
        return lobbyService.createGame(game, userId);
    }

    /*
     * Context: /lobby/games/{gameId}
     * Let's a user join a game / let's a user become a player
     * @Param Game a game body (at least all non-nullable fields), userId - User
     */
    @RequestMapping(method = RequestMethod.POST, value = "games/{gameId}")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String joinGame(@PathVariable Long gameId, @RequestParam Long userId){
        return lobbyService.joinGame(gameId, userId);
    }
}
