package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PLAY_CARD")
@DiscriminatorValue(value = GameConstants.PLAY_CARD)
@JsonTypeName(value = "PLAY_CARD")
public class PlayCardMove extends AMove {

    // Existence Reason: Hibernate also needs an empty constructor
    public PlayCardMove(){}

    public PlayCardMove(String moveType){
        super(moveType);
    }

    @Column
    private Long cardId;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}
