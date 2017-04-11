package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.site.BuildingSite;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

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

    @OneToOne(targetEntity = BuildingSite.class, fetch = FetchType.LAZY)
    private BuildingSite targetSite;

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public BuildingSite getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(BuildingSite targetSite) {
        this.targetSite = targetSite;
    }
}
