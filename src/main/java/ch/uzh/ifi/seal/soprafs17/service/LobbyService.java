package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Dave on 21.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs17.entity.Game;
import ch.uzh.ifi.seal.soprafs17.entity.User;
import ch.uzh.ifi.seal.soprafs17.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class LobbyService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final GameService gameService;


    private boolean hasBeenCreated = false;

    @Autowired
    public LobbyService(GameService gameService) {
        this.gameService = gameService;
        }

    public List<Game> listGames(){
        // TODO Change this dummy data to real implementation
        //return this.gameService.listGames();

        if (!hasBeenCreated) {
            Game game1 = gameService.createGame("dave","david",GameStatus.RUNNING);
            Game game2 = gameService.createGame("dave2","david2",GameStatus.PENDING);
            Game game3 = gameService.createGame("dave3","david3",GameStatus.RUNNING);
        }

        return gameService.listGames();
    }


}
