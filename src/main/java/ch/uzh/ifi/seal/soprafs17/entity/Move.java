package ch.uzh.ifi.seal.soprafs17.entity;

import ch.uzh.ifi.seal.soprafs17.constant.MoveType;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Move implements Serializable {
	//TODO Implement the abstract class move from the class diagram - be careful the MoveRepository doesn't work with abstract classes

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private MoveType moveType;
	
    // TODO: Needs to be changed to Rounds?
	/*@ManyToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JoinColumn(name="GAME_ID")
    private Round round;*/

	@ManyToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    @JoinColumn(name="PLAYER_ID")
	@JsonBackReference
    private Player player;

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
