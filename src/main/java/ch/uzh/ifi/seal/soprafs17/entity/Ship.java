package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class Ship implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private static final int MIN_STONES = 1;

    @Column
    public static int MAX_STONES; // add final and value

    //@OneToMany
    private ArrayList<Stone> stones = new ArrayList<Stone>();

    @ManyToOne(targetEntity = Round.class)
    private Round round;

    @ManyToOne(targetEntity = RoundCard.class)
    private RoundCard roundCard;

    private void unloadStone() { //to be filled
    }

    private void addStone(){
        //to be filled
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<Stone> getStones() {
        return stones;
    }

    public void setStones(ArrayList<Stone> stones) {
        this.stones = stones;
    }

}
