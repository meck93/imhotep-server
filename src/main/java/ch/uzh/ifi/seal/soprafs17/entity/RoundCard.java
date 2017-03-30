package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class RoundCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private RoundCardType heads;

    @Column(nullable = false)
    private ArrayList<ShipSize> shipSizes;

    @Column
    private Long gameId;

    //@OneToMany(targetEntity= Ship.class)
    //private ArrayList<Ship> ships;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoundCardType getHeads() {
        return heads;
    }

    public void setHeads(RoundCardType heads) {
        this.heads = heads;
    }

    public ArrayList<ShipSize> getShipSizes() {
        return shipSizes;
    }

    public void setShipSizes(ArrayList<ShipSize> shipSizes) {
        this.shipSizes = shipSizes;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}

