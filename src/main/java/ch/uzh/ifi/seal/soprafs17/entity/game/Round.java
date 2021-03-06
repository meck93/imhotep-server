package ch.uzh.ifi.seal.soprafs17.entity.game;

import ch.uzh.ifi.seal.soprafs17.entity.card.RoundCard;
import ch.uzh.ifi.seal.soprafs17.exceptions.http.NotFoundException;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Round implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int roundNumber;

    @OneToOne(targetEntity = RoundCard.class)
    private RoundCard card;

    @ManyToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "GAME_ID")
    @JsonBackReference
    private Game game;

    @OneToMany(targetEntity= Ship.class)
    @OrderBy
    private List<Ship> ships;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoundCard getCard() {
        return card;
    }

    public void setCard(RoundCard card) {
        this.card = card;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public Ship getShipById(Long shipId){
        Ship result = null;
        for (Ship ship : ships){
            if (ship.getId().equals(shipId)){
                result = ship;
            }
        }

        if (result == null) throw new NotFoundException(shipId, "Ship");

        return result;
    }
}
