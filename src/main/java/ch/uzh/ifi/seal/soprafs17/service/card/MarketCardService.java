package ch.uzh.ifi.seal.soprafs17.service.card;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.*;

@Service
@Transactional
public class MarketCardService {

    private final Logger log = LoggerFactory.getLogger(MarketCardService.class);

    // Constants for the MarketCard Creation
    private static final int OCCUR_TWICE = 2;
    private static final int OCCUR_THREE_TIMES = 3;
    private static final int OCCUR_TEN_TIMES = 10;
    private static final int MARKET_CARD_DECK_SIZE = 4;

    private final MarketCardRepository marketCardRepository;

    @Autowired
    public MarketCardService(MarketCardRepository marketCardRepository) {
        this.marketCardRepository = marketCardRepository;
    }

    /**
     * Creates one marketCard of a specific MarketCardType
     * @param   gameId, color, marketCardType
     * @pre     game != NULL
     * @post    marketCard is created and saved in the MarketCardRepository
     */
    public MarketCard createMarketCard(Long gameId, String color, MarketCardType marketCardType){
        log.debug("Creating a marketCard" + gameId);

        // Creating a MarketCard of Type: marketCardType
        MarketCard marketCard = new MarketCard();
        marketCard.setGameId(gameId);
        marketCard.setColor(color);
        marketCard.setMarketCardType(marketCardType);

        marketCardRepository.save(marketCard);

        return marketCard;
    }

    public MarketCard getMarketCard(Long gameId) {
        log.debug("Picking a random marketCard out of the marketCardDeck associated with gameId: " + gameId);

        List<MarketCard> marketCardDeck = new ArrayList<>();
        marketCardRepository.findAllMarketCards(gameId).forEach(marketCardDeck::add);

        // Removing all alreadyChosen marketCards from the deck
        marketCardDeck.removeIf(MarketCard::isAlreadyChosen);

        // Choosing one of the new marketCards by random
        Random rnd = new Random();
        MarketCard chosenMarketCard = marketCardDeck.get(rnd.nextInt(marketCardDeck.size()-1));

        // Marking the chosen card as used
        chosenMarketCard.setAlreadyChosen(true);
        marketCardRepository.save(chosenMarketCard);

        return chosenMarketCard;
    }

    public List<MarketCard> getMarketCardDeck(Long gameId) {
        log.debug("Get the next four Market Cards by random for the Game: " + gameId);

        List<MarketCard> result = new ArrayList<>();
        for (int i = 0; i < MARKET_CARD_DECK_SIZE; i++){
            result.add(getMarketCard(gameId));
        }

        return result;
    }

    /**
     * Creates the marketCardDeck
     * @param   gameId
     * @pre     game =/= NULL
     * @post    34 marketCards of the different marketCardTypes are created
     */
    public void createMarketCardSet(Long gameId) {
        log.debug("Creating a marketCardDeck");

        // create all the marketCards that occur two times
        for(int i=0; i<OCCUR_TWICE; i++) {
            createMarketCard(gameId, "red", PAVED_PATH);
            createMarketCard(gameId, "red", SARCOPHAGUS);
            createMarketCard(gameId, "red", ENTRANCE);
            createMarketCard(gameId, "green", PYRAMID_DECORATION);
            createMarketCard(gameId, "green", TEMPLE_DECORATION);
            createMarketCard(gameId, "green", BURIAL_CHAMBER_DECORATION);
            createMarketCard(gameId, "green", OBELISK_DECORATION);
            createMarketCard(gameId, "blue", LEVER);
            createMarketCard(gameId, "blue", HAMMER);
        }

        // create all the marketCards that occur three times
        for(int i=0; i<OCCUR_THREE_TIMES; i++) {
            createMarketCard(gameId, "blue", CHISEL);
            createMarketCard(gameId, "blue", SAIL);
        }

        // create STATUE marketCards (10 times)
        for(int i=0; i<OCCUR_TEN_TIMES;i++) {
            createMarketCard(gameId, "violet", STATUE);
        }
    }



}
