package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "GET_STONES")
@DiscriminatorValue(value = GameConstants.GET_STONES)
public class GetStonesMove extends AMove {

    public GetStonesMove(String moveType) {
        super(moveType);
    }
}
