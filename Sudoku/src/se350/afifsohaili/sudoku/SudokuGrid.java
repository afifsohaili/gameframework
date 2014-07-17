package se350.afifsohaili.sudoku;

import java.util.LinkedList;
import java.util.Random;

import se350.afifsohaili.gameframework.Grid;

/**
 * The class extends the abstract class GridData and contains the data that is used in a Sudoku game: basically, an integer.
 * @author Afif Sohaili
 */
public class SudokuGrid extends Grid<SudokuData> {
	@Override
	protected void init() {
		data = new SudokuData[verticalTiles][horizontalTiles];
		generateSudoku();
	}
	
	/**
	 * Generates the Sudoku tiles and ensure that each 3x3 boxes, rows and columns have unique integer from 1 to 9
	 */
	private void generateSudoku() {
		Random random = new Random();
		for (int row = 0; row < verticalTiles; row++) {
			LinkedList<Integer> possibleNumbers = generatePossibleNumbers();
			for (int column = 0; column < horizontalTiles;) {
				LinkedList<Integer> possibleNumbersCopy = new LinkedList<Integer>(possibleNumbers);
				int number = 0;
				boolean valid = false;
				do {
					if (possibleNumbersCopy.size() > 0) {
						number = possibleNumbersCopy.remove(random.nextInt(possibleNumbersCopy.size()));
						valid = validateNumberInColumn(number, row, column) && validateNumberInRow(number, row, column) && validateNumberInBox(number, row, column);
					}
					else {
						// backtrack
						data[row][column] = null;
						if (column > 0) {
							column--;
						}
						else {
							row--;
							column = 0;
							for (int i = 0; i < data[row].length; i++) {
								data[row][i] = null;
							}
							possibleNumbers = generatePossibleNumbers();
						}
						break;
					}
				} while (!valid);
				
				if (valid) {
					int index = 0;
					while (number != possibleNumbers.get(index)) {
						index++;
					}
					possibleNumbers.remove(index);
					data[row][column] = new SudokuData(number);
					column++;
				}
			}
			// finish generating column
		}
	}
	
	/**
	 * Used by generateSudoku() method to validate if the number is unique in a given row.
	 * @param number the number to be validated.
	 * @param row the row in which the number is to be validated.
	 * @param column the column position of the number
	 * @return true if the number is unique. Otherwise, false.
	 */
	private boolean validateNumberInRow(int number, int row, int column) {
		SudokuData num = new SudokuData(number);
		for (int i = 0; i < column; i++) {
			if (data[row][i].equals(num)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Used by generateSudoku() method to validate if the number is unique in a given column.
	 * @param number the number to be validated.
	 * @param row the row of the number
	 * @param column the column in which the number is to be validated.
	 * @return true if the number is unique. Otherwise, false.
	 */
	private boolean validateNumberInColumn(int number, int row, int column) {
		SudokuData num = new SudokuData(number);
		for (int i = 0; i < row; i++) {
			if (data[i][column].equals(num)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Used by generateSudoku() method to validate if the number is unique in its 3x3 box.
	 * @param number the number to be validated.
	 * @param row the row in which the number is to be validated.
	 * @param column the column in which the number is to be validated.
	 * @return true if the number is unique. Otherwise, false.
	 */
	private boolean validateNumberInBox(int number, int row, int column) {
		SudokuData num = new SudokuData(number);
		int rowStart = (row / (verticalTiles / 3)) * (verticalTiles / 3);
		int rowEnd = rowStart + (verticalTiles / 3);
		int columnStart = (column / (verticalTiles / 3)) * (horizontalTiles / 3);
		int columnEnd = columnStart + (horizontalTiles / 3);
		for (int i = rowStart; i < rowEnd; i++) {
			for (int j = columnStart; j < columnEnd; j++) {
				if (data[i][j] == null) {
					return true;
				}
				if (data[i][j].equals(num)) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * A helper method that generates unique numbers in the range of 1 to 9.
	 * @return unique numbers in the range of 1 to 9 in a LinkedList of Integers.
	 */
	private LinkedList<Integer> generatePossibleNumbers() {
		LinkedList<Integer> possibleNumbers = new LinkedList<Integer>();
		for (int number = 0; number < horizontalTiles; number++) {
			possibleNumbers.add(number+1);
		}
		return possibleNumbers;
	}
	
	@Override
	public SudokuData getElementAtPosition(int row, int column) {
		return new SudokuData(data[row][column].getValue());
	}

	@Override
	public boolean updateGrid(String... update) {
		return true;
	}
}