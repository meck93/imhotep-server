package ch.uzh.ifi.seal.soprafs17.entity;

import ch.uzh.ifi.seal.soprafs17.constant.GameStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String owner;
	
	@Column 
	private GameStatus status;
	
	@Column 
	private Integer currentPlayer;

	@OneToOne(targetEntity= StoneQuarry.class)
	private StoneQuarry stoneQuarry;

	/*@OneToMany(mappedBy= "game")
	private List<Round> rounds;*/

    // TODO Change to correct mapping into Player and not User
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game", orphanRemoval = true)
	@JsonManagedReference
	private List<Player> players;

    @Column
	private int roundCounter;

   /* @OneToOne(MappedBy= "game")
	private MarketPlace marketPlace;*/

   /* @OneToMany(MappedBy= "game")
	private List<IRateable> buildingSites;*/

   /*
   * Amount of players exists only for providing
   * dummy data to the client.
   */

	@Column
	private int amountOfPlayers;

	public int getAmountOfPlayers() {
		return amountOfPlayers;
	}

	public void setAmountOfPlayers(int amountOfPlayers) {
		this.amountOfPlayers = amountOfPlayers;
	}

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public GameStatus getStatus() {
		return status;
	}

	public void setStatus(GameStatus status) {
		this.status = status;
	}

	public Integer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(Integer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getRoundCounter() {
		return roundCounter;
	}

	public void setRoundCounter(int roundCounter) {
		this.roundCounter = roundCounter;
	}
}
