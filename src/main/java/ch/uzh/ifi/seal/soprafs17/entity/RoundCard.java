package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class RoundCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @JsonIgnore
    private boolean alreadyChosen;

    @Column(nullable = false)
    private RoundCardType heads;

    @ElementCollection(targetClass = ShipSize.class)
    private List<ShipSize> shipSizes;

    @Column
    private Long gameId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAlreadyChosen() {
        return alreadyChosen;
    }

    public void setAlreadyChosen(boolean alreadyChosen) {
        this.alreadyChosen = alreadyChosen;
    }

    public RoundCardType getHeads() {
        return heads;
    }

    public void setHeads(RoundCardType heads) {
        this.heads = heads;
    }

    public List<ShipSize> getShipSizes() {
        return shipSizes;
    }

    public void setShipSizes(List<ShipSize> shipSizes) {
        this.shipSizes = shipSizes;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}

