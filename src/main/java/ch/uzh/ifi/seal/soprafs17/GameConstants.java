package ch.uzh.ifi.seal.soprafs17;

public class GameConstants {

	// max rounds per game
	public static final Integer LAST_ROUND = 6;

	// max and min players per game
	public static final Integer MIN_PLAYERS = 2;
	public static final Integer MAX_PLAYERS = 4;

	// color for the players
	public static final String BROWN = "BROWN";
	public static final String GRAY = "GRAY";
	public static final String WHITE = "WHITE";
	public static final String BLACK = "BLACK";

	public static final String PYRAMID = "PYRAMID";
	public static final String TEMPLE = "TEMPLE";
	public static final String OBELISK = "OBELISK";
	public static final String BURIAL_CHAMBER = "BURIAL_CHAMBER";

	// different types of moves / discriminator values for hibernate db
	public static final String GET_STONES = "GET_STONES";
	public static final String PLACE_STONE = "PLACE_STONE";
	public static final String SAIL_SHIP = "SAIL_SHIP";
	public static final String GET_CARD = "GET_CARD";
	public static final String PLAY_CARD = "PLAY_CARD";

	// starting points
	public static final int START_POINTS = 0;

	// amount of starting stones
	public static final int START_STONES = 30;

	// max stones on StoneQuarry
	public static final int MAX_STONES_SUPPLY_SLED = 5;
	public static final int MAX_STONES_ADDED_PER_MOVE = 3;
	public static final int MIN_STONES_TO_PLACE_STONE = 1;

	// sonarquabe suggested to add a private constructor instead of the implicit public one
	private GameConstants() {};
}
