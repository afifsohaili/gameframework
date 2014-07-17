package se350.afifsohaili.sudoku;

import se350.afifsohaili.gameframework.UserInterface;

/**
 * Class that contains the main() method for the Sudoku game.
 * @author Afif Sohaili
 */
public class Driver {
	/**
	 * The project's entry point.
	 * @param args
	 */
	public static void main(String[] args) {
		SudokuMechanics mechanics = new SudokuMechanics();
		UserInterface ui = new SudokuUI();
		mechanics.attachUI(ui);
		SudokuGrid grid = new SudokuGrid();
		mechanics.attachGrid(grid);
	}
}