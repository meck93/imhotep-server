package ch.uzh.ifi.seal.soprafs17.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.awt.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Player implements Serializable {
    // TODO Implementation of the Player Entity
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    //TODO implement the correct mapping into the User and Move entity
    @OneToOne
    @JoinColumn(name="USER_ID")
    @JsonManagedReference
    private User user;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Move> moves;

    @ManyToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", foreignKey = @ForeignKey(name ="GAME_ID_FK"))
    @JsonBackReference
    private Game game;

    @Column
    private int points;

    @Column
    private Color color;

    @Column
    private int playerNumber;

    /*
    @ManyToOne or something like that
    private SupplySled supplySled;
     */

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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
}
