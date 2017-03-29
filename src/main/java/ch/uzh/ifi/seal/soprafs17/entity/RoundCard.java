package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;
import ch.uzh.ifi.seal.soprafs17.constant.ShipSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RoundCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private RoundCardType heads;

    @Column(nullable = false)
    private ArrayList<ShipSize> sizes;

    @Column
    private Long gameId;

    //@OneToMany(targetEntity= Ship.class)
    //private ArrayList<Ship> ships;

    public Long getId() {return id;}

    public ArrayList<ShipSize> getSizes() {return sizes;}

    public void setSizes(ArrayList<ShipSize> sizes) {this.sizes = sizes;}

    public void setId(Long id) {
        this.id = id;
    }

    public RoundCardType getHeads() {
        return heads;
    }

    public void setHeads(RoundCardType heads) {
        this.heads = heads;
    }
}

