package ch.uzh.ifi.seal.soprafs17.web.rest;

import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PlayerController.CONTEXT)
public class PlayerController extends GenericController {

    Logger log  = LoggerFactory.getLogger(GameController.class);

    // Standard URI Mapping of this class
    static final String CONTEXT = "/games/{gameId}/players";

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addPlayer(@PathVariable Long gameId, @RequestParam("token") String userToken) {
        return playerService.addPlayer(gameId, userToken);
    }

    @RequestMapping(value = "/{playerId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Player getPlayer(@PathVariable Long gameId, @PathVariable Integer playerId) {
        return playerService.getPlayer(gameId, playerId);
    }

    @RequestMapping( method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Player> listPlayers(@PathVariable Long gameId) {
        return playerService.getPlayers(gameId);
    }
}