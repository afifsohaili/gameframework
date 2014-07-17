package se350.afifsohaili.gameframework;

import java.net.URL;

import javax.swing.JOptionPane;

/**
 * Mechanics is the glue that holds all classes together.<br />
 * <ol>
 * <li>Mechanics is responsible for loading options from the options.ini file. The instance of Options class is static and public.</li>
 * <li>Mechanics provides a method to attach the user interface class to itself. Once attached, Mechanics will utilize the class to print, display error and update the player's view. The user interface class must implement the UserInterface interface that contains the method signatures that Mechanics will use.</li>
 * <li>Mechanics also provides a method to attach the grid class that contains the tiles' data.</li>
 * <li>Mechanics contains the methods that are important in defining the game mechanism. e.g. it handles player view generation, checking win conditions and application exits when an error is encountered.</li>
 * </ol>
 * @author Afif Sohaili
 */
public abstract class Mechanics {
	// Declaration of constants
	private static final String UI_INVALID = "Invalid user interface.";
	private static final String DATA_INVALID = "Invalid data.";
	/**
	 * The options for the game.
	 */
	public static final Options OPTIONS = new Options();
	/**
	 * The user interface for the game. Represented by a class that implements the UserInterface interface.
	 * @see UserInterface
	 */
	protected UserInterface ui;
	/**
	 * The representation of data inside each tiles.
	 * @see Grid
	 */
	protected Grid<? extends Data<?>> gridData;
	
	/**
	 * Attach the class that extends the Grid abstract class that contains the tiles' data.
	 * @param gridData any class that extends the Grid abstract class. This class will represent the data of the application.
	 * @see Grid
	 */
	public void attachGrid(Grid<? extends Data<?>> gridData) {
		if (gridData == null) {
			displayError(DATA_INVALID);
			return;
		}
		if (gridData.validateGrid()) {
			this.gridData = gridData;
			customInit();
			ui.run(this);
			ui.updateUI(generatePlayerView());
		}
		else {
			displayError(DATA_INVALID);
		}
	}

	/**
	 * Called directly before the game is run. By default, it does nothing. 
	 * However, game programmers can override this method in their implementation of class Mechanics to perform any necessary operations before the game starts.
	 */
	protected void customInit() {
		// do nothing by default.
	}

	/**
	 * Attach a class that implements the UserInterface interface to this class.
	 * Other than providing an interface for the players, the path for the given class will also be used to find the location of the game's options.ini file.
	 * @param ui A class that implements the UserInterface interface
	 * @see UserInterface
	 */
	public void attachUI(UserInterface ui) {
		if (ui == null) {
			displayError(UI_INVALID);
		}
		this.ui = ui;
		URL optionsFileURL = ui.getClass().getResource(Options.FILEPATH);
		OPTIONS.loadOptionsFromFile(optionsFileURL);
	}
	
	/**
	 * Display error using the method signature given in UserInterface interface. If an error occured before a UserInterface class is attached to the class, the JOptionPane.showMessageDialog will be used to display the error.
	 * @param errorMsg the error message to be shown.
	 * @see UserInterface
	 */
	protected void displayError(String errorMsg) {
		if (ui != null) {
			ui.displayError(errorMsg);
			ui.exit();
		}
		else {
			JOptionPane.showMessageDialog(null, UI_INVALID, UI_INVALID, JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
	}
	
	/**
	 * Generate the view for players. It will be used with updateUI() method signature in UserInterface interface to generate the player's view.
	 * @return data that is used to generate the view for the player
	 */
	protected abstract String[][] generatePlayerView();
	
	/**
	 * Used to define the game logic. Check if the player has win. 
	 * @param data The data (usually the input from users) that is used by the game logic to determine if the player has won.
	 * @return true if the player has won. Otherwise, false.
	 */
	public abstract boolean checkWinCondition(Data<?>[][] data);
	
	/**
	 * Get the option based from the given key.
	 * @param key the option key
	 * @return the option value.
	 */
	public String getOption(String key) {
		return new String(OPTIONS.getOption(key));
	}
	
	/**
	 * Get the number of horizontal tiles in the grid as set in the option.
	 * @return the number of horizontal tiles in the grid
	 */
	public static int getHorizontalTiles() {
		return Integer.parseInt(OPTIONS.getOption(Options.HORIZONTAL_TILES));
	}
	
	/**
	 * Get the number of vertical tiles in the grid as set in the option.
	 * @return the number of vertical tiles in the grid
	 */
	public static int getVerticalTiles() {
		return Integer.parseInt(OPTIONS.getOption(Options.VERTICAL_TILES));
	}
	
	/**
	 * Accept input passed by users.
	 * @param input String... allows the game developers to pass the data String in the parameter as: input("This","is","an","example"), and the compiler will turn the Strings passed as String string[] = {"This", "is", "an", "example"}.
	 */
	public abstract void input(String... input);
}