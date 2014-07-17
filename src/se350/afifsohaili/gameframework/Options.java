package se350.afifsohaili.gameframework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * A class that stores the application's options.<br/>
 * The Options class automatically generate two options on constructor call:
 * <ol>
 * <li>The number of horizontal tiles in the grid</li>
 * <li>The number of vertical tiles in the grid.</li>
 * </ol>
 * Both options can be overriden through the options.ini file. 
 * The options.ini file should always be located under the same source folder of the class that implements the UserInterface interface, and must be located in a folder called "options" 
 * @author Afif Sohaili
 */
public class Options {
	// constant declaration
	/**
	 * Key for option value that defines the number of horizontal tiles in the game.
	 */
	static final String HORIZONTAL_TILES = "horizontaltiles";
	/**
	 * Key for option value that defines the number of vertical tiles in the game.
	 */
	static final String VERTICAL_TILES = "verticaltiles";
	/**
	 * The file path for options.ini.
	 */
	static final String FILEPATH = "/options/options.ini";
	// member fields
	/**
	 * The HashMap that stores the options. The options should be defined as:
	 * <br/>
	 * string key=option
	 */
	private HashMap<String, String> options;
	private LinkedList<Exception> exceptions;
	
	/**
	 * The constructor call will put two default options:<ol><li>The number of horizontal tiles and</li><li>The number of vertical tiles.</li></ol>
	 */
	public Options() {
		exceptions = new LinkedList<Exception>();
		options = new HashMap<String, String>();
		options.put(HORIZONTAL_TILES, "10");
		options.put(VERTICAL_TILES, "10");
	}
	
	/**
	 * Load options from the given file URL.
	 * @param optionsFileURL
	 */
	void loadOptionsFromFile(URL optionsFileURL) {
		if (optionsFileURL != null) {
			BufferedReader fileReader = null;
			try {
				fileReader = new BufferedReader(new InputStreamReader(optionsFileURL.openStream()));
				String line = fileReader.readLine();
				
				while (line != null) {
					String[] option = line.split("=");
					String key = option[0].toLowerCase().trim();
					String value = option[1].trim();
					if (options.containsKey(key)) {
						options.remove(key);
					}
					options.put(key, value);
					line = fileReader.readLine();
				}
			} catch (IOException e) {
				exceptions.add(e);
			}
			finally {
				if (fileReader != null) {
					try {
						fileReader.close();
					} catch (IOException e) {
						// display error
					}
				}
			}
		} // end if
		validateDefaultSettings();
	}
	
	/**
	 * Return the stored exceptions that the Options class has encountered.\n
	 * The list of exception is made available to only the framework.
	 * @return the instance to the LinkedList of the exceptions.
	 */
	LinkedList<Exception> getError() {
		return exceptions;
	}

	/**
	 * Validates the default settings of the options that must be a specific format.\n
	 * e.g. Horizontal tiles and vertical tiles need to be integers.
	 */
	private void validateDefaultSettings() {
		if (options.containsKey(HORIZONTAL_TILES)) {
			if (!options.get(HORIZONTAL_TILES).matches("[1-9][0-9]{0,8}")) {
				exceptions.add(new NumberFormatException(HORIZONTAL_TILES));
			}
		}
		if (options.containsKey(VERTICAL_TILES)) {
			if (!options.get(VERTICAL_TILES).matches("[1-9][0-9]{0,8}")) {
				exceptions.add(new NumberFormatException(VERTICAL_TILES));
			}
		}
	}
	
	/**
	 * Returns the value to the given key from the sets of option.
	 * @param key key to a specific option
	 * @return the option value to the given option key.
	 */
	public String getOption(String key) {
		return new String(options.get(key.toLowerCase().trim()));
	}
}
