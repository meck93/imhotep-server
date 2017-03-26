package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;

@Entity
public class StoneQuarry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private Game game;

    private ArrayList<Stone> blackStones= new ArrayList<Stone>();
    private ArrayList<Stone> whiteStones= new ArrayList<Stone>();
    private ArrayList<Stone> brownStones= new ArrayList<Stone>();
    private ArrayList<Stone> grayStones= new ArrayList<Stone>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ArrayList<Stone> getBlackStones() {
        return blackStones;
    }

    public void setBlackStones(ArrayList<Stone> blackStones) {
        this.blackStones = blackStones;
    }

    public ArrayList<Stone> getWhiteStones() {
        return whiteStones;
    }

    public void setWhiteStones(ArrayList<Stone> whiteStones) {
        this.whiteStones = whiteStones;
    }

    public ArrayList<Stone> getBrownStones() {
        return brownStones;
    }

    public void setBrownStones(ArrayList<Stone> brownStones) {
        this.brownStones = brownStones;
    }

    public ArrayList<Stone> getGrayStones() {
        return grayStones;
    }

    public void setGrayStones(ArrayList<Stone> grayStones) {
        this.grayStones = grayStones;
    }
}
