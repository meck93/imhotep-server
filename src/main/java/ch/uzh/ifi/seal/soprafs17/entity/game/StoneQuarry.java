package ch.uzh.ifi.seal.soprafs17.entity.game;

import ch.uzh.ifi.seal.soprafs17.exceptions.InternalServerException;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "StoneQuarry")
public class StoneQuarry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Game game;

    @OneToMany
    @JsonIgnore
    private List<Stone> blackStones;

    @OneToMany
    @JsonIgnore
    private List<Stone> whiteStones;

    @OneToMany
    @JsonIgnore
    private List<Stone> brownStones;

    @OneToMany
    @JsonIgnore
    private List<Stone> grayStones;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Stone> getStonesByPlayerNr(int playerNr) {
        switch (playerNr){
            case 1: return getBlackStones();
            case 2: return getWhiteStones();
            case 3: return getBrownStones();
            case 4: return getGrayStones();
            default: throw new InternalServerException("No correct playerNr between 1-4 was specified");
        }
    }
}