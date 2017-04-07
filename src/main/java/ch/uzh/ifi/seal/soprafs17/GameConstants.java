package ch.uzh.ifi.seal.soprafs17;

public class GameConstants {

	// max and min players per game
	public final static Integer MIN_PLAYERS = 2;
	public final static Integer MAX_PLAYERS = 4;

	// color for the players
	public final static String BROWN = "BROWN";
	public final static String GRAY = "GRAY";
	public final static String WHITE = "WHITE";
	public final static String BLACK = "BLACK";

	// different types of moves / discriminator values for hibernate db
	public final static String GET_STONES = "GET_STONES";
	public final static String PLACE_STONE = "PLACE_STONE";
	public final static String SAIL_SHIP = "SAIL_SHIP";
	public final static String PLAY_CARD = "PLAY_CARD";

	// starting points
	public final static int START_POINTS = 0;

	// amount of starting stones
	public final static int START_STONES = 30;

	// max stones on StoneQuarry
	public final static int MAX_STONES_SUPPLY_SLED = 5;

	// sonarquabe suggested to add a private constructor instead of the implicit public one
	private GameConstants() {};
}
