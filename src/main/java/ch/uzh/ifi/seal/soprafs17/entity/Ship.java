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
    private int minStone;

    @Column
    private int maxStone;

    //@OneToMany
    // YOU ARE NOT ALLOWED TO DO  new ArrayList() here
    private ArrayList<Stone> stones;

    @ManyToOne(targetEntity = Round.class)
    private Round round;

    @ManyToOne(targetEntity = RoundCard.class)
    private RoundCard roundCard;

    public Ship(int min, int max){
        this.setMaxStone(max);
        this.setMinStone(min);
    }

    public Ship() {}

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

    public int getMinStone() {
        return minStone;
    }

    public void setMinStone(int minStone) {
        this.minStone = minStone;
    }

    public int getMaxStone() {
        return maxStone;
    }

    public void setMaxStone(int maxStone) {
        this.maxStone = maxStone;
    }
}
