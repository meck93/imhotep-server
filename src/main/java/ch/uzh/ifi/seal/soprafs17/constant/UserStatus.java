package ch.uzh.ifi.seal.soprafs17.constant;

import java.util.Set;
import java.util.stream.Collector;

public enum UserStatus {
	/*
	* Online: the user is currently active in a game as a player
	* Offline: the user is currently in the lobby and not actively taking part in game
	* This is only a basic idea as with the template the user appears OFFLINE after the login.
	*/
	ONLINE, OFFLINE;
}
