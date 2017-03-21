package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


/**
 * Service class for managing users.
 */
@Service
@Transactional
public class PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    private final GameService gameService;
    private final UserService userService;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, GameService gameService, UserService userService) {
        this.playerRepository = playerRepository;
        this.gameService = gameService;
        this.userService = userService;
    }

    // TODO check the implementation and handling of this method
    public String addPlayer(Long gameId, String userToken) {
       /* log.debug("addPlayer: " + userToken);

        Game game = gameService.getGame(gameId);
        User player = userService.getUserByToken(userToken);

        if (game != null && player != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);
            log.debug("Game: " + game.getName() + " - player added: " + player.getUsername());
            return "games" + "/" + gameId + "/players/" + (game.getPlayers().size() - 1);
        }

        else {
            log.error("Error adding player with token: " + userToken);
        }
*/
        return null;
    }

    public User getPlayer(Long gameId, Integer playerId) {
        log.debug("getPlayer: " + gameId);

        Game game = gameService.getGame(gameId);
        // TODO implement checking if game exisits

        //TODO maybe check if player exists
        game.getPlayers().get(playerId);

        return null;
    }

    public ArrayList<Player> getPlayers(Long gameId) {
        log.debug("listPlayers");

        /*// TODO implement getPlayers in either userService or playerService

        Game game = gameRepository.findOne(gameId);
        if (game != null) {
            // Maybe as a List
            return game.getPlayers();
        }*/

        return null;
    }
}
