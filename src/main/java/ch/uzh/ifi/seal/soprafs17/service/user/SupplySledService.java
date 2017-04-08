package ch.uzh.ifi.seal.soprafs17.service.user;

import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import ch.uzh.ifi.seal.soprafs17.entity.user.Player;
import ch.uzh.ifi.seal.soprafs17.entity.user.SupplySled;
import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
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

    private static final int MAX_STONES = 5;

    private final SupplySledRepository supplySledRepository;
    private final StoneQuarryService stoneQuarryService;

    @Autowired
    public SupplySledService(SupplySledRepository supplySledRepository, StoneQuarryService stoneQuarryService) {
        this.supplySledRepository = supplySledRepository;
        this.stoneQuarryService = stoneQuarryService;
    }

    public SupplySled createSupplySled(Player player){
        log.debug("creating SupplySled for Player with playerId: " + player.getId());

        // Creating the SupplySled for a Player
        SupplySled supplySled = new SupplySled();
        supplySled.setStones(new ArrayList<>());
        supplySled.setPlayer(player);

        supplySledRepository.save(supplySled);

        return supplySled;
    }

    /*
     * @Param SupplySled, Stone to be added
     * This methods adds a stone to the supply sled
     */
    public void addStone(SupplySled supplySled, Stone stone) {
        log.debug("Adding stone to SupplySled");

        if (supplySled.getStones().size() < MAX_STONES) {
            List<Stone> stones = supplySled.getStones();
            stones.add(stone);
            supplySled.setStones(stones);

            supplySledRepository.save(supplySled);
        }
        else {
            throw new InternalServerException("The SupplySled is already full: failed to add a stone!");
        }
    }

    public SupplySled getSupplySledByGameId(Long gameId, int playerNumber) {
        log.debug("Retrieving the Supply Sled of Player: " + playerNumber + " in Game: " + gameId);

        List<SupplySled> supplySleds = (List<SupplySled>) supplySledRepository.findAll();
        SupplySled returnSled = null;

        // Check whether the SupplySled belongs to the Player: playerNumber in Game: gameId
        for (SupplySled supplySled : supplySleds){
            if (supplySled.getPlayer().getPlayerNumber() == playerNumber
                    && supplySled.getPlayer().getGame().getId().equals(gameId)){
                returnSled = supplySled;
            }
        }

        if (returnSled == null) {
            String msg = "SupplySled for Player: " + playerNumber + "in Game:";
            throw new NotFoundException(gameId, msg);
        }

        return returnSled;
    }

    public void fillSupplySled(Long gameId, int playerNumber, int stonesToBeAdded){
        log.debug("Filling a SupplySled for Player: " + playerNumber + " in Game: " + gameId);

        SupplySled supplySled = this.getSupplySledByGameId(gameId, playerNumber);

        // Retrieving the list of Stones
        List<Stone> stones = supplySled.getStones();

        // Add the correct Amount of Stones
        for (int i = 0; i < stonesToBeAdded; i++) {
            stones.add(stoneQuarryService.getStone(gameId, playerNumber));
        }
        // Adding the list of Stones back to the SupplySled
        supplySled.setStones(stones);

        supplySledRepository.save(supplySled);
    }

    public void fillSupplySleds(Game game) {
        log.debug("Filling all SupplySleds in Game: " + game.getId());

        // The initial player gets 2 initial Stones, the 2nd 3 initial Stones
        fillSupplySled(game.getId(), 1, 2);
        fillSupplySled(game.getId(), 2, 3);

        if (game.getPlayers().size() > 2){
            // The 3rd player gets 4 initial Stones
            fillSupplySled(game.getId(), 3, 4);
        }
        if (game.getPlayers().size() > 3){
            // The 4th player gets 5 initial Stones
            fillSupplySled(game.getId(), 4, 5);
        }
    }
}
