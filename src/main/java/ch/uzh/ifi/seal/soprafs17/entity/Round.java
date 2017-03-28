package ch.uzh.ifi.seal.soprafs17.entity;

/**
 * Created by Cristian on 25.03.2017.
 */

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
    private RoundCard card;

    @OneToMany
    private List<Move> moves;

    @ManyToOne
    @JsonBackReference
    private Game game;

    @OneToMany(targetEntity= Ship.class)
    private List<Ship> ships;

    public RoundCard getCard() {
        return card;
    }

    public void setCard(RoundCard card) {
        this.card = card;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
