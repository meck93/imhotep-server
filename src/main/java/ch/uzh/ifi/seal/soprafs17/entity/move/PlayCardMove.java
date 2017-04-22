package ch.uzh.ifi.seal.soprafs17.entity.move;


import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.ArrayList;

@Entity(name = "PLAY_CARD")
@DiscriminatorValue(value = GameConstants.PLAY_CARD)
@JsonTypeName(value = "PLAY_CARD")
public class PlayCardMove extends AMove {

    public PlayCardMove(){
        // Existence Reason: Hibernate also needs an empty constructor
    }

    public PlayCardMove(String moveType){
        super(moveType);
    }

    @Column
    private Long cardId;

    @Column
    private Long shipId;

    @Column
    private int placeOnShip;

    @Column
    private long targetSiteId;

    @Column
    private long shipId2;

    @Column
    private int placeOnShip2;

    @Column
    private ArrayList<Long> unloadingOrder;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public int getPlaceOnShip() {
        return placeOnShip;
    }

    public void setPlaceOnShip(int placeOnShip) {
        this.placeOnShip = placeOnShip;
    }

    public long getTargetSiteId() {
        return targetSiteId;
    }

    public void setTargetSiteId(long targetSiteId) {
        this.targetSiteId = targetSiteId;
    }

    public long getShipId2() {
        return shipId2;
    }

    public void setShipId2(long shipId2) {
        this.shipId2 = shipId2;
    }

    public int getPlaceOnShip2() {
        return placeOnShip2;
    }

    public void setPlaceOnShip2(int placeOnShip2) {
        this.placeOnShip2 = placeOnShip2;
    }

    public ArrayList<Long> getUnloadingOrder() {
        return unloadingOrder;
    }

    public void setUnloadingOrder(ArrayList<Long> unloadingOrder) {
        this.unloadingOrder = unloadingOrder;
    }
}
