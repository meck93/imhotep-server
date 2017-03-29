package ch.uzh.ifi.seal.soprafs17.service;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import ch.uzh.ifi.seal.soprafs17.entity.Round;
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

        if (amountOfPlayers == 2){createSevenTwoHeadCards(gameId);}
        if (amountOfPlayers == 3){createSevenThreeHeadCards();}
        if (amountOfPlayers == 4){createSevenFourHeadCards();}
    }

    /**
     * Creates a deck of seven roundcards with two heads.
     * @param gameId
     * @pre game =/= NULL
     * @post seven roundcards are stored under the game id
     */
    public void createSevenTwoHeadCards(Long gameId){

        ArrayList<ShipSize> shipsR1 = new ArrayList<>();
        RoundCard roundCard1 = new RoundCard();
        roundCard1.setId(gameId);
        roundCard1.setHeads(RoundCardType.TWO_HEADS);
        shipsR1.add(ShipSize.XL);
        shipsR1.add(ShipSize.L);
        shipsR1.add(ShipSize.M);
        shipsR1.add(ShipSize.M);
        roundCardRepository.save(roundCard1);

        ArrayList<ShipSize> shipsR2 = new ArrayList<>();
        RoundCard roundCard2 = new RoundCard();
        roundCard2.setId(gameId);
        roundCard2.setHeads(RoundCardType.TWO_HEADS);
        shipsR2.add(ShipSize.XL);
        shipsR2.add(ShipSize.L);
        shipsR2.add(ShipSize.L);
        shipsR2.add(ShipSize.S);
        roundCardRepository.save(roundCard2);

        ArrayList<ShipSize> shipsR3 = new ArrayList<>();
        RoundCard roundCard3 = new RoundCard();
        roundCard3.setId(gameId);
        roundCard3.setHeads(RoundCardType.TWO_HEADS);
        shipsR3.add(ShipSize.XL);
        shipsR3.add(ShipSize.M);
        shipsR3.add(ShipSize.M);
        shipsR3.add(ShipSize.S);
        roundCardRepository.save(roundCard3);

        ArrayList<ShipSize> shipsR4 = new ArrayList<>();
        RoundCard roundCard4 = new RoundCard();
        roundCard4.setId(gameId);
        roundCard4.setHeads(RoundCardType.TWO_HEADS);
        shipsR4.add(ShipSize.L);
        shipsR4.add(ShipSize.M);
        shipsR4.add(ShipSize.M);
        shipsR4.add(ShipSize.S);
        roundCardRepository.save(roundCard4);

        ArrayList<ShipSize> shipsR5 = new ArrayList<>();
        RoundCard roundCard5 = new RoundCard();
        roundCard5.setId(gameId);
        roundCard5.setHeads(RoundCardType.TWO_HEADS);
        shipsR5.add(ShipSize.L);
        shipsR5.add(ShipSize.L);
        shipsR5.add(ShipSize.M);
        shipsR5.add(ShipSize.M);
        roundCardRepository.save(roundCard5);

        ArrayList<ShipSize> shipsR6 = new ArrayList<>();
        RoundCard roundCard6 = new RoundCard();
        roundCard6.setId(gameId);
        roundCard6.setHeads(RoundCardType.TWO_HEADS);
        shipsR6.add(ShipSize.L);
        shipsR6.add(ShipSize.L);
        shipsR6.add(ShipSize.M);
        shipsR6.add(ShipSize.S);
        roundCardRepository.save(roundCard6);

        ArrayList<ShipSize> shipsR7 = new ArrayList<>();
        RoundCard roundCard7 = new RoundCard();
        roundCard7.setId(gameId);
        roundCard7.setHeads(RoundCardType.TWO_HEADS);
        shipsR7.add(ShipSize.XL);
        shipsR7.add(ShipSize.L);
        shipsR7.add(ShipSize.M);
        shipsR7.add(ShipSize.S);
        roundCardRepository.save(roundCard7);

    };
    public void createSevenThreeHeadCards(){};
    public void createSevenFourHeadCards(){};

    /**
     * Deals a random card from the currently available roundcards.
     * @param gameId
     * @pre game =/= NULL && game.roundCounter =l= 6
     * @return RoundCard roundcard
     */
    public RoundCard getRoundCard(Long gameId) {

        List<RoundCard> deck = roundCardRepository.findAllRoundCards(gameId);
        Random rnd = new Random();
        RoundCard currCard = deck.get(rnd.nextInt(6));

        return currCard;
    }

    public void deleteCard(RoundCard roundCard){roundCardRepository.delete(roundCard);}
}
