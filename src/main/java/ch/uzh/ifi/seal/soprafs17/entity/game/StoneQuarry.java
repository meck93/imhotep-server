package ch.uzh.ifi.seal.soprafs17.entity.game;

/**
 * Created by Cristian on 26.03.2017.
 */

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "StoneQuarry")
public class StoneQuarry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stone> blackStones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stone> whiteStones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stone> brownStones;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stone> grayStones;

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

        List<Stone> result = null;

        switch (playerNr){
            case 1: result = getBlackStones(); break;
            case 2: result = getWhiteStones(); break;
            case 3: result = getBrownStones(); break;
            case 4: result = getGrayStones(); break;
        }
        return result;
    }
}