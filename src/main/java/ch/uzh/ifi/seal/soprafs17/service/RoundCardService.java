package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoundCardService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final RoundCardRepository roundCardRepository;

    @Autowired
    public RoundCardService(RoundCardRepository roundCardRepository) {
        this.roundCardRepository = roundCardRepository;
    }

    public void createRoundCards(int amountOfPlayers, Long gameId) {
        // TODO: Create roundCards according to the amount of players in a game
        // TODO: save roundsCards to roundCardRepo

        // Create each roundCard and set all variables -> save to repo
    }

    public RoundCard getRoundCard(Long gameId) {
        // TODO: return RoundCard from the Repo
        // 1. Get all RoundCards associated with gameId
        // 2. Check which one doesn't have a RoundId
        // 3. Choose one of the remaining
        // OR make it with delete RoundCard from Repo

        return null;
    }

}
