package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @Column
    private Long gameId;

    @OneToMany(targetEntity = Stone.class)
    private List<Stone> stones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(List<Stone> stones) {
        this.stones = stones;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
