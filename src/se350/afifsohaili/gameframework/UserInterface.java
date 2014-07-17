package se350.afifsohaili.gameframework;

/**
 * This interface provides the method signatures that are used by Mechanics class to start the game and update the UserInterface. 
 * Apart from that, it also defines how the game should exit or display error messages if an error is encountered.
 * @author Afif Sohaili
 */
public interface UserInterface {
	/**
	 * Starts the game.
	 * @param mechanics the game mechanics. Sometimes, starting the game require some methods from the Mechanics class. e.g. {@link Mechanics#getHorizontalTiles()}
	 * @see Mechanics
	 */
	void run(Mechanics mechanics);
	/**
	 * Allows the UserInterface to be updated.
	 * @param data contains the data. The method should process the given data and updates the UserInterface accordingly.
	 */
	void updateUI(String[][] data);
	/**
	 * Used to display error messages to users.
	 * @param errorMsg the error message.
	 */
	void displayError(String errorMsg);
	/**
	 * Defines how the game exit.
	 */
	void exit();
}