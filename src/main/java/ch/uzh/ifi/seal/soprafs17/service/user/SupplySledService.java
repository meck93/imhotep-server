package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import ch.uzh.ifi.seal.soprafs17.repository.SupplySledRepository;
import ch.uzh.ifi.seal.soprafs17.service.game.StoneQuarryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SupplySledService {

    private final Logger log = LoggerFactory.getLogger(SupplySledService.class);

    private final SupplySledRepository supplySledRepository;
    private final StoneQuarryService stoneQuarryService;

    @Autowired
    public SupplySledService(SupplySledRepository supplySledRepository, StoneQuarryService stoneQuarryService) {
        this.supplySledRepository = supplySledRepository;
        this.stoneQuarryService = stoneQuarryService;
    }

    public SupplySled createSupplySled(Long playerId){
        log.debug("creating SupplySled for Player with playerId: " + playerId);

        // Creating the SupplySled for a Player
        SupplySled supplySled = new SupplySled();
        supplySled.setStones(new ArrayList<>());
        supplySled.setPlayerId(playerId);

        supplySledRepository.save(supplySled);

        return supplySled;
    }


    public SupplySled getSupplySledByGameId(Game game, Long playerId) {
        log.debug("Retrieving the Supply Sled of Player: " + playerId + " in Game: " + game.getId());

        List<SupplySled> supplySleds = (List<SupplySled>) supplySledRepository.findAll();

        // Check whether the SupplySled belongs to the Player: playerNumber in Game: gameId
        for (SupplySled supplySled : supplySleds){
            if (supplySled.getPlayerId().equals(playerId)){
                return supplySled;
            }
        }
        String msg = "SupplySled for Player: " + playerId + "in Game:";
        throw new NotFoundException(game.getId(), msg);
    }

    public void fillSupplySled(Game game, int playerNumber, int stonesToBeAdded){
        log.debug("Filling a SupplySled for Player: " + playerNumber + " in Game: " + game.getId());

        SupplySled supplySled = this.getSupplySledByGameId(game, game.getPlayerByPlayerNr(playerNumber).getId());

        // Retrieving the list of Stones
        List<Stone> stones = supplySled.getStones();

        // Add the correct Amount of Stones
        for (int i = 0; i < stonesToBeAdded; i++) {
            stones.add(stoneQuarryService.getStone(game.getId(), playerNumber));
        }
        // Adding the list of Stones back to the SupplySled
        supplySled.setStones(stones);

        supplySledRepository.save(supplySled);
    }

    public void fillSupplySleds(Game game) {
        log.debug("Filling all SupplySleds in Game: " + game.getId());

        // The initial player gets 2 initial Stones, the 2nd 3 initial Stones
        fillSupplySled(game, 1, 2);
        fillSupplySled(game, 2, 3);

        if (game.getPlayers().size() > 2){
            // The 3rd player gets 4 initial Stones
            fillSupplySled(game, 3, 4);
        }
        if (game.getPlayers().size() > 3){
            // The 4th player gets 5 initial Stones
            fillSupplySled(game, 4, 5);
        }
    }
}
