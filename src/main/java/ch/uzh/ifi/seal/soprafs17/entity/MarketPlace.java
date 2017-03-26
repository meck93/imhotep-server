package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class MarketPlace implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    //OneToMany
    private ArrayList<MarketCard> marketCards = new ArrayList<MarketCard>();

    private void initialize() {

    }

    private void removeCard() {

    }

    private void chooseCard() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<MarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(ArrayList<MarketCard> marketCards) {
        this.marketCards = marketCards;
    }
}
