package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
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

    @Column
    private Long marketCardId;

    public Long getMarketCardId() {
        return marketCardId;
    }

    public void setMarketCardId(Long marketCardId) {
        this.marketCardId = marketCardId;
    }
}
