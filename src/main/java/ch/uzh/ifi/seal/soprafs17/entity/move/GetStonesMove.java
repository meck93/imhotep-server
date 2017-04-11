package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "GET_STONES")
@DiscriminatorValue(value = GameConstants.GET_STONES)
@JsonTypeName(value = "GET_STONES")
public class GetStonesMove extends AMove {

    // Existence Reason: Hibernate also needs an empty constructor
    public GetStonesMove(){}

    public GetStonesMove(String moveType) {
        super(moveType);
    }
}
