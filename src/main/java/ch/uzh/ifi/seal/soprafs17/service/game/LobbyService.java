package ch.uzh.ifi.seal.soprafs17.service.game;


import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.service.GameService;
import ch.uzh.ifi.seal.soprafs17.service.user.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(LobbyService.class);
    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public LobbyService(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
        }

    /*
     * This method returns a list of all games
     */
    public List<Game> listGames(){
        return gameService.listGames();
    }

    /*
     * Calls the gameService to create a Game
     */
    public Game createGame(Game game, Long userId) {
        // Creates a new game
        Game newGame = gameService.createGame(game.getName(), game.getOwner());

        // Creates a new player from the user who created the game
        Player player = playerService.createPlayer(newGame.getId(), userId);

        // Initializing the new player
        String playerMapping = playerService.initializePlayer(newGame.getId(), player);

        return newGame;
    }
    /*
     * Implementation of a User joining a Game. User -> Player. Player -> Joins Game.
     */
    public String joinGame(Long gameId, Long userId){
        // Creating a Player
        Player player = playerService.createPlayer(gameId, userId);

        // Initializing the new player
        return playerService.initializePlayer(gameId, player);
    }
}
