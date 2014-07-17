package se350.afifsohaili.bejeweled;

import java.util.LinkedList;
import java.util.Random;

import se350.afifsohaili.gameframework.Grid;

/**
 * @author Afif Sohaili
 */
public class BejeweledGrid extends Grid<BejeweledData> {
	@Override
	protected void init() {
		data = new BejeweledData[verticalTiles][horizontalTiles];
	}

	void generateBejeweledGrid() {
		Random random = new Random();
		// generate
		for (int row = 0; row < verticalTiles; row++) {
			for (int column = 0; column < horizontalTiles; column++) {
				if (data[row][column] == null) {
					LinkedList<Integer> colorChoices = getColorChoices();
					boolean valid = false;
					int color = 0;
					do {
						if (colorChoices.size() > 0) {
							color = colorChoices.remove(random.nextInt(colorChoices.size()));
							valid =	validateLeftBoxes(color, row, column) &&
									validateTopBoxes(color, row, column);
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
							}
							break;
						}
					} while (!valid);
					
					if (valid) {
						data[row][column] = new BejeweledData(color);
					}
				}
			}
			// finish for loop column
		}
		// finish for loop row
	}

	private LinkedList<Integer> getColorChoices() {
		return new LinkedList<Integer>(BejeweledMechanics.COLORS.values());
	}

	private boolean validateLeftBoxes(int colorChoice, int row, int column) {
		if (column > 1) {
			BejeweledData color = new BejeweledData(colorChoice);
			if (color.equals(data[row][column - 1]) && color.equals(data[row][column - 2])) {
				return false;
			}
		}
		return true;
	}

	private boolean validateTopBoxes(int colorChoice, int row, int column) {
		if (row > 1) {
			BejeweledData color = new BejeweledData(colorChoice);
			if (color.equals(data[row - 1][column]) && color.equals(data[row - 2][column])) {
				return false;
			}
		}
		return true;
	}

	@Override
	public BejeweledData getElementAtPosition(int row, int column) {
		return new BejeweledData(data[row][column].getValue());
	}

	@Override
	public boolean updateGrid(String... update) {
		// store original data
		BejeweledData[][] originalData = createDataCopy();
		
		// get the coordinates of the two clicked item
		String positionSelected[] = update[0].split(",");
		String positionClicked[] = update[1].split(",");
		
		// prepare proper variables
		int rowDifference = Integer.parseInt(positionClicked[0]) - Integer.parseInt(positionSelected[0]);
		int columnDifference = Integer.parseInt(positionClicked[1]) - Integer.parseInt(positionSelected[1]);
		int rowSelected = Integer.parseInt(positionSelected[0]);
		int columnSelected = Integer.parseInt(positionSelected[1]);
		int rowClicked = Integer.parseInt(positionClicked[0]);
		int columnClicked = Integer.parseInt(positionClicked[1]);
		
		// check if the two clicked item is side-by-side
		if (Math.abs(rowDifference) == 0 && Math.abs(columnDifference) == 1 || 
				Math.abs(rowDifference) == 1 && Math.abs(columnDifference) == 0) {
			// checks if the change creates a line of 3 items with the same color.
			BejeweledData temp = data[rowSelected][columnSelected];
			data[rowSelected][columnSelected] = data[rowClicked][columnClicked];
			data[rowClicked][columnClicked] = temp;
			temp = null;
			boolean explode = explode();
			if (explode) {
				while (explode()) {
					
				}
				return true;
			}
			else {
				// release
				data = null;
				// restore original data (before swapping)
				data = originalData;
				
				return false;
			}
		}
		return false;
	}

	private boolean explode() {
		for (int row = 0; row < verticalTiles; row++) {
			for (int column = 0; column < horizontalTiles; column++) {
				boolean explode = false;
				if (row < 2 && column < 2) {
					continue;
				}
				// checks if the change creates a horizontal line of 3 items with the same color.
				explode = !(validateLeftBoxes(data[row][column].getValue().getRGB(), row, column));
				if (explode) {
					// copy all colors above down one level.
					BejeweledData[][] dataCopy = createDataCopy();
					for (int r = 1; r <= row; r++) {
						data[r][column] = dataCopy[r-1][column];
						data[r][column - 1] = dataCopy[r-1][column - 1];
						data[r][column - 2] = dataCopy[r-1][column - 2];
					}
					for (int i = 0, r = 0; i < 2; i++) {
						data[r][column - i] = null;
					}
					// release
					dataCopy = null;
					// generate the topmost tiles
					generateBejeweledGrid();
					return true;
				}
				// checks if the change creates a horizontal line of 3 items with the same color.
				explode = !(validateTopBoxes(data[row][column].getValue().getRGB(), row, column));
				if (explode) {
					// copy all colors above down one level.
					BejeweledData[][] dataCopy = createDataCopy();
					for (int r = 0; r < row - 2; r++) {
						data[r+2][column] = dataCopy[r][column];
					}
					for (int r = 0; r < 3; r++) {
						data[r][column] = null;
					}
					// release
					dataCopy = null;
					// generate the topmost tiles
					generateBejeweledGrid();
					return true;
				}
			}
		}
		return false;
	}
	
	private BejeweledData[][] createDataCopy() {
		BejeweledData[][] copy = new BejeweledData[verticalTiles][horizontalTiles];
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				copy[i][j] = data[i][j];
			}
		}
		return copy;
	}
}