package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "GET_CARD")
@DiscriminatorValue(value = GameConstants.GET_CARD)
@JsonTypeName(value = "GET_CARD")
public class GetCardMove extends AMove {

    public GetCardMove() {
        // Existence Reason: Hibernate also needs an empty constructor
    }

    public GetCardMove(String moveType){
        super(moveType);
    }

    @Column
    private Long marketCardId;

    @Column
    private MarketCardType marketCardType;

    public MarketCardType getMarketCardType() {
        return marketCardType;
    }

    public void setMarketCardType(MarketCardType marketCardType) {
        this.marketCardType = marketCardType;
    }

    public Long getMarketCardId() {
        return marketCardId;
    }

    public void setMarketCardId(Long marketCardId) {
        this.marketCardId = marketCardId;
    }
}
