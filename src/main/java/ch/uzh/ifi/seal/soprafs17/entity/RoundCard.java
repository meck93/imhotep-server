package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.RoundCardType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class RoundCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Column
    private RoundCardType heads;

    //ManyToOne
    private ArrayList<Ship> ships = new ArrayList<Ship>();

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
}

