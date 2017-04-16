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


    public SailShipMove(){
        // Existence Reason: Hibernate also needs an empty constructor
    }

    public SailShipMove(String moveType){
        super(moveType);
    }

    @Column
    private Long shipId;

    @Column
    private long targetSiteId;

    @Column(name = "SITE_TYPE", updatable = false, insertable = false)
    private String targetSiteType;

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public String getTargetSiteType() {
        return targetSiteType;
    }

    public void setTargetSiteType(String targetSiteType) {
        this.targetSiteType = targetSiteType;
    }

    public long getTargetSiteId() {
        return targetSiteId;
    }

    public void setTargetSiteId(long targetSiteId) {
        this.targetSiteId = targetSiteId;
    }
}
