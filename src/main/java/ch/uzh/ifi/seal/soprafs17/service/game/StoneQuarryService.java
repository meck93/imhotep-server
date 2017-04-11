package ch.uzh.ifi.seal.soprafs17.service.game;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.game.StoneQuarry;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.StoneQuarryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StoneQuarryService {

    private final Logger log = LoggerFactory.getLogger(StoneQuarryService.class);

    private final StoneQuarryRepository stoneQuarryRepository;
    private final StoneService stoneService;

    @Autowired
    public StoneQuarryService(StoneQuarryRepository stoneQuarryRepository, StoneService stoneService) {
        this.stoneQuarryRepository = stoneQuarryRepository;
        this.stoneService = stoneService;
    }

    public StoneQuarry createStoneQuarry(Game game){
        log.debug("Creating a StoneQuarry");

        StoneQuarry stoneQuarry = new StoneQuarry();
        stoneQuarry.setGame(game);

        // Creating the StoneQuarry for the minimum Nr of Players
        stoneQuarry.setBlackStones(new ArrayList<>());
        stoneQuarry.setWhiteStones(new ArrayList<>());

        // Creating 1 additional StoneQuarry for Player Nr 3
        if (game.getPlayers().size() > 2){
            stoneQuarry.setBrownStones(new ArrayList<>());
        }
        // Creating 1 additional StoneQuarry for Player Nr 4
        if (game.getPlayers().size() > 3){
            stoneQuarry.setGrayStones(new ArrayList<>());
        }

        stoneQuarryRepository.save(stoneQuarry);

        return stoneQuarry;
    }

    public void fillQuarry(StoneQuarry stoneQuarry){
        log.debug("Filling the StoneQuarry with the initial stones of each color");

        // Filling the StoneQuarry of the first two Players
        stoneQuarry.setBlackStones(stoneService.createStones(GameConstants.BLACK));
        stoneQuarry.setWhiteStones(stoneService.createStones(GameConstants.WHITE));

        // Filling the StoneQuarry for PlayerNr: 3
        if (stoneQuarry.getGame().getPlayers().size() > 2){
            stoneQuarry.setBrownStones(stoneService.createStones(GameConstants.BROWN));
        }
        // Filling the StoneQuarry for PlayerNr: 4
        if (stoneQuarry.getGame().getPlayers().size() > 3){
            stoneQuarry.setGrayStones(stoneService.createStones(GameConstants.GRAY));
        }

        stoneQuarryRepository.save(stoneQuarry);
    }

    public Stone getStone(Long gameId, int playerNumber){
        log.debug("Returning a Stone from Quarry of Game: " + gameId);

        // Retrieving the StoneQuarry
        StoneQuarry stoneQuarry = this.getStoneQuarryByGameId(gameId);
        // Retrieving the correctly colored stones
        List<Stone> stones = stoneQuarry.getStonesByPlayerNr(playerNumber);
        Stone stone;

        // Checking that the List of Stones is neither null nor empty
        if (stones != null && !stones.isEmpty()) {
            // Distinguishing the returnStone
            stone = stones.remove(0);
        }
        else {
            throw new InternalServerException("StoneQuarry is null or empty!");
        }
        // Checking that the removed stone is not null
        if (stone == null) throw new InternalServerException("Stone in the StoneQuarry is null");

        stoneQuarryRepository.save(stoneQuarry);

        return stone;
    }

    public StoneQuarry getStoneQuarryByGameId(Long gameId){
        log.debug("Find StoneQuarry by GameId: " + gameId);

        List<StoneQuarry> stoneQuarrys = (List<StoneQuarry>) stoneQuarryRepository.findAll();
        StoneQuarry returnQuarry = null;

        // Find the StoneQuarry which belongs to the Game: gameId
        for (StoneQuarry stoneQuarry : stoneQuarrys){
            if (stoneQuarry.getGame().getId().equals(gameId)){
                returnQuarry = stoneQuarry;
            }
        }

        if (returnQuarry == null) throw new NotFoundException(gameId, "StoneQuarry in Game:");

        return returnQuarry;
    }
}