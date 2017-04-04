package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity(name = "MarketPlace")
@DiscriminatorValue("MARKET_PLACE")
public class MarketPlace extends ASite implements Serializable{

    public MarketPlace(){};

    public MarketPlace(Long gameId){
        super.setGameId(gameId);
        super.setSiteType("MARKET_PLACE");
    }

    @OneToMany(targetEntity = MarketCard.class/*, cascade = CascadeType.ALL, orphanRemoval = true*/)
    private List<MarketCard> marketCards;

    public List<MarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<MarketCard> marketCards) {
        this.marketCards = marketCards;
    }
}
