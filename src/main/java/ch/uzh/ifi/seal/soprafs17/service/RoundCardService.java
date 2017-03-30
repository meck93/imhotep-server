package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.RoundCard;
import ch.uzh.ifi.seal.soprafs17.repository.RoundCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class RoundCardService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);
    private final RoundCardRepository roundCardRepository;

    @Autowired
    public RoundCardService(RoundCardRepository roundCardRepository) {
        this.roundCardRepository = roundCardRepository;
    }

    /**
     * Calls the appropriate method for creating a deck of cards according to the rules.
     * @param amountOfPlayers
     * @param gameId
     */
    public void createRoundCards(int amountOfPlayers, Long gameId) {
        // decide which card set to create
        switch (amountOfPlayers) {
            case 2:
                createRoundCardSet(gameId, RoundCardType.TWO_HEADS);
                break;
            case 3:
                createRoundCardSet(gameId, RoundCardType.THREE_HEADS);
                break;
            case 4:
                createRoundCardSet(gameId, RoundCardType.FOUR_HEADS);
                break;
        }
    }


    /**
     * Creates a deck of seven roundcards with two heads.
     * @param gameId
     * @pre game =/= NULL
     * @post seven roundcards are stored under the game id
     */
    public void createRoundCardSet(Long gameId, RoundCardType roundCardType){
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.L, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.M, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.XL, ShipSize.L, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.M, ShipSize.M, ShipSize.S);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.M, ShipSize.M);
        createRoundCard(gameId, roundCardType, ShipSize.L, ShipSize.L, ShipSize.M, ShipSize.S);
    }

    public void createRoundCard(Long gameId, RoundCardType roundCardType, ShipSize sizeOne, ShipSize sizeTwo, ShipSize sizeThree, ShipSize sizeFour) {

        RoundCard roundCard = new RoundCard();
        roundCard.setGameId(gameId);
        roundCard.setHeads(roundCardType);

        ArrayList<ShipSize> shipSizes = new ArrayList<>();
        shipSizes.add(sizeOne);
        shipSizes.add(sizeTwo);
        shipSizes.add(sizeThree);
        shipSizes.add(sizeFour);
        roundCard.setShipSizes(shipSizes);

        roundCardRepository.save(roundCard);
    }

    /**
     * Deals a random card from the currently available roundcards.
     * @param gameId
     * @pre game =/= NULL && game.roundCounter =l= 6
     * @return RoundCard roundcard
     */
    public RoundCard getRoundCard(Long gameId) {

        List<RoundCard> deck = new ArrayList<>();
        roundCardRepository.findAllRoundCards(gameId).forEach(deck::add);

        Random rnd = new Random();
        RoundCard currentCard = deck.get(rnd.nextInt(deck.size()-1));

        return currentCard;
    }

    public void deleteCard(RoundCard roundCard){
        roundCardRepository.delete(roundCard);
    }
}
