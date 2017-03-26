
package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Service?
@Entity
public class SupplySled implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private static final int MAX_STONES = 5;

    private Player player;

    private ArrayList<Stone> stones= new ArrayList<Stone>();

    public void initialize(){}

    public void addStone(){}

    public void removeStone(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static int getMaxStones() {
        return MAX_STONES;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Stone> getStones() {
        return stones;
    }

    public void setStones(ArrayList<Stone> stones) {
        this.stones = stones;
    }
}
