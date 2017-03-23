package ch.uzh.ifi.seal.soprafs17.service;

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.Player;
import ch.uzh.ifi.seal.soprafs17.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * Service class for managing players.
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

        // Cristi and daves code
        Game game = gameService.getGame(gameId);
        User user = userService.getUserByToken(userToken);
        Player player = new Player(user);

        if (game != null && user != null && game.getPlayers().size() < GameConstants.MAX_PLAYERS) {
            game.getPlayers().add(player);

            log.debug("Game: " + game.getName() + " - player added: " + player.getUser().getUsername());
            return "games" + "/" + gameId + "/players/" + (game.getPlayers().size() - 1);
        }

        else {
            log.error("Error adding player with token: " + userToken);
        }

        // end of our code

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

    public Player getPlayer(Long gameId, Integer playerId) {
        log.debug("getPlayer: " + gameId);

        Game game = gameService.getGame(gameId);
        // TODO implement checking if game exisits

        //TODO maybe check if player exists
        game.getPlayers().get(playerId);

        return null;
    }

    public List<Player> getPlayers(Long gameId) {
        log.debug("listPlayers of Game " + gameId);

        List<Player> result = new ArrayList<>();
        playerRepository.findAll().forEach(result::add);

        return result;
    }
}
