package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.BuildingSite;

import javax.persistence.*;

@Entity(name = "SAIL_SHIP")
@DiscriminatorValue(value = GameConstants.SAIL_SHIP)
public class SailShipMove extends AMove {

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
