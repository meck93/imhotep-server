package ch.uzh.ifi.seal.soprafs17.entity.move;

/**
 * Created by Cristian on 15.04.2017.
 */

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "GET_CARD")
@DiscriminatorValue(value = GameConstants.GET_CARD)
@JsonTypeName(value = "GET_CARD")
public class GetCardMove extends AMove {

    @Column
    private Long currentSubRoundPlayerId;

    @Column
    private Long marketCardId;

    public Long getCurrentSubRoundPlayerId() {
        return currentSubRoundPlayerId;
    }

    public void setCurrentSubRoundPlayerId(Long currentSubRoundPlayerId) {
        this.currentSubRoundPlayerId = currentSubRoundPlayerId;
    }

    public Long getMarketCardId() {
        return marketCardId;
    }

    public void setMarketCardId(Long marketCardId) {
        this.marketCardId = marketCardId;
    }
}
