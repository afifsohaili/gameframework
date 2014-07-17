package se350.afifsohaili.bejeweled;

import static org.junit.Assert.*;

import org.junit.Test;

import se350.afifsohaili.gameframework.Mechanics;

/**
 * @author Afif Sohaili
 */
public class BejeweledGridTest {
	BejeweledMechanics mechanics;
	BejeweledUI ui;
	BejeweledGrid grid;
	
	public void main() {
		mechanics = new BejeweledMechanics();
		ui = new BejeweledUI();
		mechanics.attachUI(ui);
		grid = new BejeweledGrid();
		mechanics.attachGrid(grid);
	}
	
	@Test
	public void generateBejeweledGridTest() {
		main();
		// test
		for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				assertTrue(grid.getElementAtPosition(row, column) != null);
				boolean horizontalValid = false, verticalValid = false;
				if (column > 1) {
					horizontalValid = grid.getElementAtPosition(row, column).equals(grid.getElementAtPosition(row, column - 1)) &&
							grid.getElementAtPosition(row, column).equals(grid.getElementAtPosition(row, column - 2));
				}
				if (row > 1) {
					verticalValid = grid.getElementAtPosition(row, column).equals(grid.getElementAtPosition(row - 1, column)) &&
							grid.getElementAtPosition(row, column).equals(grid.getElementAtPosition(row - 2, column));
				}
				assertTrue(horizontalValid && verticalValid);
			}
		}
	}

}
