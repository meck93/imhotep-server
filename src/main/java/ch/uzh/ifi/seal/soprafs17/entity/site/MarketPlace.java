package ch.uzh.ifi.seal.soprafs17.entity.site;

import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;

import javax.persistence.*;
import java.util.List;

@Entity(name = "MarketPlace")
@DiscriminatorValue("MARKET_PLACE")
public class MarketPlace extends ASite{

    public MarketPlace(){};

    public MarketPlace(Long gameId){
        super.setGameId(gameId);
        super.setSiteType("MARKET_PLACE");
    }

    @Column
    private Long dockedShipId;

    @OneToMany(targetEntity = MarketCard.class)
    @OrderBy("id ASC")
    private List<MarketCard> marketCards;

    public Long getDockedShipId() {
        return dockedShipId;
    }

    public void setDockedShipId(Long dockedShipId) {
        this.dockedShipId = dockedShipId;
    }

    public List<MarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<MarketCard> marketCards) {
        this.marketCards = marketCards;
    }

    public MarketCard getMarketCardById(Long marketCardId) {
        for (MarketCard marketCard : this.marketCards){
            if (marketCard.getId().equals(marketCardId)){
                return marketCard;
            }
        }
        throw new NotFoundException(marketCardId, "MarketCardId");
    }
}
