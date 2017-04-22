package ch.uzh.ifi.seal.soprafs17.entity.user;

import ch.uzh.ifi.seal.soprafs17.entity.card.MarketCard;
import ch.uzh.ifi.seal.soprafs17.entity.game.Game;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long playerId;

    @Column(name = "id")
    private Long id;

    @Column
    private String username;

    @Column
    private int[] points;

    @Column
    private String color;

    @Column
    private int playerNumber;

    @OneToOne
    @JoinColumn(name="USER_ID")
    @JsonBackReference(value = "user")
    private User user;

    @ManyToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name ="GAME_ID_FK"))
    @JsonBackReference
    private Game game;

    @OneToOne(targetEntity = SupplySled.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private SupplySled supplySled;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MarketCard> handCards;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getGameId(){
        return game.getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int[] getPoints() {
        return points;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<MarketCard> getHandCards() {
        return handCards;
    }

    public void setHandCards(List<MarketCard> handCards) {
        this.handCards = handCards;
    }

    public MarketCard getMarketCardById(Long marketCardId){
        for (MarketCard marketCard : this.getHandCards()){
            if (marketCard.getId().equals(marketCardId)){
                return marketCard;
            }
        }
        throw new NotFoundException(marketCardId, "MarketCard");
    }
}