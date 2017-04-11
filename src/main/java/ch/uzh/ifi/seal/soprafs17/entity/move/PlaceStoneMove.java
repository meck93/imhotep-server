package ch.uzh.ifi.seal.soprafs17.entity.move;

import ch.uzh.ifi.seal.soprafs17.GameConstants;
import ch.uzh.ifi.seal.soprafs17.entity.game.Stone;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.*;

@Entity(name = "PLACE_STONE")
@DiscriminatorValue(value = GameConstants.PLACE_STONE)
@JsonTypeName(value = "PLACE_STONE")
public class PlaceStoneMove extends AMove {

    public PlaceStoneMove(){}

    public PlaceStoneMove(String moveType){
        super(moveType);
    }

    @Column
    private Long shipId;

    @Column
    private int placeOnShip;

    @OneToOne(targetEntity = Stone.class, fetch = FetchType.LAZY)
    private Stone stone;

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

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }
}
