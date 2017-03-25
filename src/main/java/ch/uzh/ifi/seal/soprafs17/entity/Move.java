package ch.uzh.ifi.seal.soprafs17.entity;

import ch.uzh.ifi.seal.soprafs17.constant.MoveType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Move implements Serializable {
	//TODO Implement the abstract class move from the class diagram - be careful the MoveRepository doesn't work with abstract classes

	/**
	 *  Implements the Move class
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column
	private String name;

	@NotNull
	@Column
	private MoveType moveType;
	
    @ManyToOne
    @JoinColumn(name="GAME_ID")
    private Game game;
    
    @ManyToOne
    @JoinColumn(name="PLAYER_ID")
    private Player player;

    @ManyToOne(targetEntity = Round.class)
    private Round round;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public void setMoveType(MoveType moveType) {
		this.moveType = moveType;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
