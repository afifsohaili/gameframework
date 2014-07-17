package se350.afifsohaili.sudoku;

import se350.afifsohaili.gameframework.Data;
/**
 * SudokuNumber is the Data interface implementation that represent the data in the grid. It is represented by an Integer.
 * @author BORHAN
 *
 */
public class SudokuData implements Data<Integer>{
	int number;
	
	/**
	 * Construct a new SudokuNumber instance.
	 * @param number the number that represents the number in the grid.
	 */
	public SudokuData(int number) {
		this.number = number;
	}
	
	@Override
	public Integer getValue() {
		return new Integer(number);
	}
	
	/**
	 * Helper method that checks if the number representation is equal to the given SudokuNumber instance number representation.
	 * @param s the SudokuNumber instance to be used in comparison.
	 * @return true if the numbers are equal. Otherwise, false.
	 */
	public boolean equals(SudokuData s) {
		if (this.number == s.getValue()) {
			return true;
		}
		return false;
	}
}
