package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.MarketCard;
import ch.uzh.ifi.seal.soprafs17.repository.MarketCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static ch.uzh.ifi.seal.soprafs17.constant.MarketCardType.*;

@Service
@Transactional
public class MarketCardService {

    private final Logger log = LoggerFactory.getLogger(MarketCardService.class);
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

        // Removing all alreadyChosen roundCards from the deck
       /*for (MarketCard marketCard : marketCardDeck) {
            if (marketCard.isAlreadyChosen()) {
                marketCardDeck.remove(marketCard);
            }
        }*/

        for(Iterator<MarketCard> it = marketCardDeck.iterator(); it.hasNext();){
            MarketCard marketCard = it.next();
            if (marketCard.isAlreadyChosen()) {
                it.remove();
            }
        }

        // Choosing one of the new marketCards by random
        Random rnd = new Random();
        MarketCard chosenMarketCard = marketCardDeck.get(rnd.nextInt(marketCardDeck.size()-1));

        // Marking the chosen card as used
        chosenMarketCard.setAlreadyChosen(true);
        marketCardRepository.save(chosenMarketCard);

        return chosenMarketCard;
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
        for(int i=0; i<2; i++) {
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
        for(int i=0; i<3; i++) {
            createMarketCard(gameId, "blue", CHISEL);
            createMarketCard(gameId, "blue", SAIL);
        }

        // create STATUE marketCards
        for(int i=0; i<10;i++) {
            createMarketCard(gameId, "violet", STATUE);
        }
    }



}
