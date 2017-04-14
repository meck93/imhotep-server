package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SAIL_SHIP")
@DiscriminatorValue(value = GameConstants.SAIL_SHIP)
@JsonTypeName(value = "SAIL_SHIP")
public class SailShipMove extends AMove {

    // Existence Reason: Hibernate also needs an empty constructor
    public SailShipMove(){}

    public SailShipMove(String moveType){
        super(moveType);
    }

    @Column
    private Long shipId;

    @Column
    private String targetSite;

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public String getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(String targetSite) {
        this.targetSite = targetSite;
    }
}
