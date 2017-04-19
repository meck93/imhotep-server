package ch.uzh.ifi.seal.soprafs17.entity.card;

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class MarketCard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String color;

    @Column
    private Long gameId;

    @Column(nullable = false)
    @JsonIgnore
    private boolean alreadyChosen;

    @Column(nullable = false)
    private MarketCardType marketCardType;

    public MarketCardType getMarketCardType() {
        return marketCardType;
    }

    public void setMarketCardType(MarketCardType marketCardType) {
        this.marketCardType = marketCardType;
    }

    public boolean isAlreadyChosen() {
        return alreadyChosen;
    }

    public void setAlreadyChosen(boolean alreadyChosen) {
        this.alreadyChosen = alreadyChosen;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
