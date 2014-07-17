package se350.afifsohaili.gameframework;

/**
 * Stores data for each tiles.
 * 
 * @author Afif Sohaili
 * @param <T> The data inside each grid represented by a class that implements the Data interface..
 */
public abstract class Grid<T extends Data<?>> {
	/**
	 * The two-dimensional array that represents the grid data.
	 */
	protected T[][] data = null;
	/**
	 * The number of horizontal tiles and vertical tiles, as set in the option.
	 */
	protected int horizontalTiles, verticalTiles;
	
	/**
	 * Initialize the grid.
	 */
	public Grid() {
		this.horizontalTiles = Mechanics.getHorizontalTiles();
		this.verticalTiles = Mechanics.getVerticalTiles();
		init();
	}

	/**
	 * Checks if the array that stores the data of the tiles is null.
	 * If it is indeed null, then the grid is not valid. False is returned. Otherwise, true is returned.
	 * @return True if the grid is not null. Otherwise, false.
	 */
	boolean validateGrid() {
		if (data == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * User-defined initialization of the grid array.
	 */
	protected abstract void init();
	
	/**
	 * Returns the data at the given row and column from the grid.
	 * @param row row of the data
	 * @param column column of the data
	 * @return the data at the given row and column from the grid.
	 */
	public abstract T getElementAtPosition(int row, int column);
	
	/**
	 * Update the grid based on user input. Called by Mechanics class every time an input is accepted from the user.
	 * @param update updates based on user input. The string that is passed to this method depends on the implementation of {@link Mechanics#input(String...) input()}.
	 * @return true if there are changes to the grid data, false otherwise.
	 */
	public abstract boolean updateGrid(String... update);
}
