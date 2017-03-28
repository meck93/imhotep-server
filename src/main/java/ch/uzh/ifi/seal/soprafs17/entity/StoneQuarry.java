package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class StoneQuarry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = Game.class)
    private Game game;

    @OneToMany
    private List<Stone> blackStones;

    @OneToMany
    private List<Stone> whiteStones;

    @OneToMany
    private List<Stone> brownStones;

    @OneToMany
    private List<Stone> grayStones;

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

    public List<Stone> getBlackStones() {
        return blackStones;
    }

    public void setBlackStones(List<Stone> blackStones) {
        this.blackStones = blackStones;
    }

    public List<Stone> getWhiteStones() {
        return whiteStones;
    }

    public void setWhiteStones(List<Stone> whiteStones) {
        this.whiteStones = whiteStones;
    }

    public List<Stone> getBrownStones() {
        return brownStones;
    }

    public void setBrownStones(List<Stone> brownStones) {
        this.brownStones = brownStones;
    }

    public List<Stone> getGrayStones() {
        return grayStones;
    }

    public void setGrayStones(List<Stone> grayStones) {
        this.grayStones = grayStones;
    }
}