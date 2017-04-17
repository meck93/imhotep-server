package ch.uzh.ifi.seal.soprafs17.entity.card;

/**
 * Created by Cristian on 25.03.2017.
 */

import ch.uzh.ifi.seal.soprafs17.constant.MarketCardType;
import ch.uzh.ifi.seal.soprafs17.entity.site.MarketPlace;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
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
    private boolean alreadyChosen; //for marketCardDeck

    @Column(nullable = false)
    private MarketCardType marketCardType;

    @ManyToOne(targetEntity = MarketPlace.class, fetch = FetchType.LAZY)
    private MarketPlace marketPlace;

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
