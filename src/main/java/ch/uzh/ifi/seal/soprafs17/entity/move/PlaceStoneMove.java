package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PLACE_STONE")
@DiscriminatorValue(value = GameConstants.PLACE_STONE)
@JsonTypeName(value = "PLACE_STONE")
public class PlaceStoneMove extends AMove {

    public PlaceStoneMove(){
        // Existence Reason: Hibernate also needs an empty constructor
    }

    public PlaceStoneMove(String moveType){
        super(moveType);
    }

    @Column
    private Long shipId;

    @Column
    private int placeOnShip;

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
}
