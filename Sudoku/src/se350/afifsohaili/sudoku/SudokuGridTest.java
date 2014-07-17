package se350.afifsohaili.sudoku;

import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import org.junit.Test;

/**
 * JUnit test case that tests the algorithm to generate the Sudoku puzzle.\n
 * Adjustments need to be done to the test case now since the framework and the Sudoku application has changed a lot.
 * @author Afif Sohaili
 */
public class SudokuGridTest {
	@Test
	public void generateSudokuTest() {
		SudokuGrid grid = new SudokuGrid();
		SudokuNumber[][] numberGrid = (SudokuNumber[][]) grid.getGrid();
		LinkedList<Integer> allNums = new LinkedList<Integer>();
		
		// Test that there are 1-9 in every rows
		for (int row = 0; row < numberGrid.length; row++) {
			allNums.clear();
			for (int column = 0; column < numberGrid[row].length; column++) {
				if (!allNums.contains(numberGrid[row][column].getValue())) {
					allNums.add(numberGrid[row][column].getValue());
				}
			}
			assertTrue(allNums.size() == numberGrid[row].length);
		}
		
		// Test that there are 1-9 in every columns
		for (int column = 0; column < numberGrid[0].length; column++) {
			allNums.clear();
			for (int row = 0; row < numberGrid.length; row++) {
				if (!allNums.contains(numberGrid[row][column].getValue())) {
					allNums.add(numberGrid[row][column].getValue());
				}
			}
			assertTrue(allNums.size() == numberGrid.length);
		}
		
		// Test that there are 1-9 in every 3x3 boxes
		for (int column = 0; column < numberGrid.length; column+=3) {
			allNums.clear();
			for (int row = 0; row < numberGrid[column].length; row+=3) {
				int rowStart = row;
				int rowEnd = row + 3;
				int columnStart = column;
				int columnEnd = column + 3;
				
				for (int i = rowStart; i < rowEnd; i++) {
					for (int j = columnStart; j < columnEnd; j++) {
						if (!allNums.contains(numberGrid[i][j].getValue())) {
							allNums.add(numberGrid[i][j].getValue());
						}
					}
				}
				
				assertTrue(allNums.size() == numberGrid.length);
			}
		}
	}
}
