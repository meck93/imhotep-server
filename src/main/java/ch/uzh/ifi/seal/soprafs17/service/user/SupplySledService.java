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

    private static final int twoStones = 2;
    private static final int threeStones = 3;
    private static final int fourStones = 4;
    private static final int fiveStones = 5;

    private final SupplySledRepository supplySledRepository;
    private final StoneQuarryService stoneQuarryService;

    @Autowired
    public SupplySledService(SupplySledRepository supplySledRepository, StoneQuarryService stoneQuarryService) {
        this.supplySledRepository = supplySledRepository;
        this.stoneQuarryService = stoneQuarryService;
    }

    public SupplySled createSupplySled(Long playerId){
        log.debug("creating SupplySled for Player with playerId: " + playerId);

        // Retrieving a possible existing SupplySled
        SupplySled supplySled = this.supplySledRepository.findSupplySledByPlayerId(playerId);

        // Checking if the SupplySled already exists
        if (supplySled != null){
            // Resetting the Stones on the SupplySled
            supplySled.setStones(new ArrayList<>());
            this.supplySledRepository.save(supplySled);

            return supplySled;
        }

        // Creating the SupplySled for a Player
        supplySled = new SupplySled();
        supplySled.setStones(new ArrayList<>());
        supplySled.setPlayerId(playerId);

        this.supplySledRepository.save(supplySled);

        return supplySled;
    }

    protected SupplySled getSupplySledByPlayerId(Long playerId) throws NotFoundException {
        log.debug("Retrieving the SupplySled for Player: " + playerId);

        SupplySled supplySled = this.supplySledRepository.findSupplySledByPlayerId(playerId);

        if (supplySled == null) throw new NotFoundException(playerId, "SupplySled for Player: ");

        return supplySled;
    }

    private void fillSupplySled(Game game, int playerNumber, int stonesToBeAdded){
        log.debug("Filling a SupplySled for Player: " + playerNumber + " in Game: " + game.getId());

        // Retrieving the correct SupplySled according to the Player's ID
        SupplySled supplySled = this.getSupplySledByPlayerId(game.getPlayerByPlayerNr(playerNumber).getId());

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
        this.fillSupplySled(game, 1, twoStones);
        this.fillSupplySled(game, 2, threeStones);

        if (game.getPlayers().size() > 2){
            // The 3rd player gets 4 initial Stones
            this.fillSupplySled(game, 3, fourStones);
        }
        if (game.getPlayers().size() > 3){
            // The 4th player gets 5 initial Stones
            this.fillSupplySled(game, 4, fiveStones);
        }
    }
}
