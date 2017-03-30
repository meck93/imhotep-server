package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class MarketPlace implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(targetEntity = MarketCard.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketCard> marketCards;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<MarketCard> marketCards) {
        this.marketCards = marketCards;
    }
}
