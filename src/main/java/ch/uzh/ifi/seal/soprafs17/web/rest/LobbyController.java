package ch.uzh.ifi.seal.soprafs17.web.rest;

/**
 * Created by Dave on 21.03.2017.
 */

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.LobbyService;
import ch.uzh.ifi.seal.soprafs17.service.PlayerService;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;

@RestController
@RequestMapping(LobbyController.CONTEXT)
public class LobbyController {

    Logger log  = LoggerFactory.getLogger(GameController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/lobby";

    private LobbyService lobbyService;

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
     * @Param Game a game body (at least all non-nullable fields), User
     */
    @RequestMapping(method = RequestMethod.POST, value = "games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Game createGame(@RequestBody Game game, @RequestParam Long userId){
        return lobbyService.createGame(game, userId);
    }
}
