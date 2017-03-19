package ch.uzh.ifi.seal.soprafs17.entity;

import ch.uzh.ifi.seal.soprafs17.constant.MoveType;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @JoinColumn(name="USER_ID")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
