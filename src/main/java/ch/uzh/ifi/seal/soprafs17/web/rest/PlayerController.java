package ch.uzh.ifi.seal.soprafs17.web.rest;

import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Move;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import ch.uzh.ifi.seal.soprafs17.repository.UserRepository;

@RestController
@RequestMapping(PlayerController.CONTEXT)
public class PlayerController extends GenericController {

    Logger log  = LoggerFactory.getLogger(GameController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games/{gameId}/players";

    private GameService gameService;
    private UserService userService;

    @Autowired
    public PlayerController(GameService gameService, UserService userService){
        this.gameService = gameService;
        this.userService = userService;
    }

    //TODO Everything below here concerning the player must be moved from here

    @RequestMapping(value = CONTEXT + "/{gameId}/players", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public String addPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        log.debug("addPlayer: " + userToken);

        Game game = gameRepo.findOne(gameId);
        User player = userRepo.findByToken(userToken);

        if (game != null && player != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);
            log.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return CONTEXT + "/" + gameId + "/player/" + (game.getPlayers().size() - 1);
        }

        else {
            log.error("Error adding player with token: " + userToken);
        }
        return null;
    }

    @RequestMapping(value = CONTEXT + "/{gameId}/players/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public User getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {
        log.debug("getPlayer: " + gameId);

        Game game = gameRepository.findOne(gameId);

        return game.getPlayers().get(playerId);
    }

}