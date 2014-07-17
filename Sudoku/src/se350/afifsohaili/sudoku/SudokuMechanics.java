package se350.afifsohaili.sudoku;

import java.util.Random;

import se350.afifsohaili.gameframework.Data;
import se350.afifsohaili.gameframework.Mechanics;

/**
 * SudokuMechanics implement the abstract methods of the framework's abstract Mechanics class:<br/>
 * 1) generatePlayerView()<br/>
 * 2) checkWinCondition()<br/>
 * @author Afif Sohaili
 */
public class SudokuMechanics extends Mechanics {
	@Override
	protected String[][] generatePlayerView() {
		String[][] data = new String[Mechanics.getVerticalTiles()][Mechanics.getHorizontalTiles()];
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
				int randomColumn = random.nextInt(Mechanics.getHorizontalTiles());
				while (data[row][randomColumn] != null) {
					randomColumn = random.nextInt(Mechanics.getVerticalTiles());
				}
				Integer number = (Integer) gridData.getElementAtPosition(row, randomColumn).getValue();
				data[row][randomColumn] = number.toString();
			}
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				int randomRow = random.nextInt(Mechanics.getVerticalTiles());
				while (data[randomRow][column] != null) {
					randomRow = random.nextInt(Mechanics.getVerticalTiles());
				}
				Integer number = (Integer) gridData.getElementAtPosition(randomRow, column).getValue();
				data[randomRow][column] = number.toString();
			}
		}
		return data;
	}

	@Override
	public boolean checkWinCondition(Data<?>[][] data) {
		for (int row = 0; row < getVerticalTiles(); row++) {
			for (int column = 0; column < getHorizontalTiles(); column++) {
				SudokuData number = (SudokuData) data[row][column];
				SudokuData numberOnModel = (SudokuData) gridData.getElementAtPosition(row, column);
				if (!number.equals(numberOnModel) || number == null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void input(String... input) {
		// do nothing
	}
}
