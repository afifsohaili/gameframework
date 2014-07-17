package se350.afifsohaili.gameframework;

/**
 * A wrapper for the data inside each tile.
 * 
 * @author Afif Sohaili
 * @param <T> The Class of the value that the tile contains. It can be anything from integers, strings, booleans or any class that you created yourself.
 */
public interface Data<T> {
	/**
	 * Returns the value that the tile contains.
	 * @return the value that represent the data.
	 */
	T getValue();
}