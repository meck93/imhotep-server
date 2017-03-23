package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Dave on 21.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final GameService gameService;

    @Autowired
    public LobbyService(GameService gameService) {
        this.gameService = gameService;
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
    public Game createGame(Game game, String userToken){
        Game newGame = gameService.createGame(game.getName(), game.getOwner());
        //Player newPlayer = playerService.createPlayer(userToken);
        return newGame;
    }




}
