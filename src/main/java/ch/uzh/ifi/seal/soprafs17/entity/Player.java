package ch.uzh.ifi.seal.soprafs17.entity;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Player implements Serializable {
    // TODO Implementation of the Player Entity
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(optional = false, targetEntity = User.class, mappedBy = "user")
    private User user;

    @OneToMany(mappedBy = "player")
    private List<Move> moves;

    @OneToMany(mappedBy = "player")
    private List<MarketCard> marketCards;

    @Column
    private int points;

    @Column
    private Color color;

    @Column
    private int playerNumber;

    @OneToOne(optional = false)
    private SupplySled supplySled;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<MarketCard> getMarketCards() {
        return marketCards;
    }

    public void setMarketCards(List<MarketCard> marketCards) {
        this.marketCards = marketCards;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public SupplySled getSupplySled() {
        return supplySled;
    }

    public void setSupplySled(SupplySled supplySled) {
        this.supplySled = supplySled;
    }
}
