package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.site.ASite;
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
    private ASite targetSite;

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

    public ASite getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(ASite targetSite) {
        this.targetSite = targetSite;
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
