package se350.afifsohaili.bejeweled;

import java.util.HashMap;
import se350.afifsohaili.gameframework.Data;
import se350.afifsohaili.gameframework.Mechanics;

/**
 * @author Afif Sohaili
 */
public class BejeweledMechanics extends Mechanics {
	private static final String COLOR_OPTIONS = "colors";
	/**
	 * Stores the different colors as defined in the options.
	 */
	static final HashMap<String, Integer> COLORS = new HashMap<String, Integer>();
	static final String PREFIX_COLOR_OPTION = "color";
	
	/**
	 * Store the colors as defined in the options in a HashMap.
	 */
	@Override
	protected void customInit() {
		getColorOptions();
		((BejeweledGrid)gridData).generateBejeweledGrid();
	}
	
	@Override
	protected String[][] generatePlayerView() {
		String[][] data = new String[getVerticalTiles()][getHorizontalTiles()];
		for (int row = 0; row < getVerticalTiles(); row++) {
			for (int column = 0; column < getHorizontalTiles(); column++) {
				Integer color = ((BejeweledGrid)gridData).getElementAtPosition(row, column).getValue().getRGB();
				data[row][column] = new String(color.toString());
			}
		}
		return data;
	}

	@Override
	public boolean checkWinCondition(Data<?>[][] data) {
		return true;
	}
	
	private void getColorOptions() {
		String[] colors = getOption(COLOR_OPTIONS).split(",");
		for (int i = 0; i < colors.length; i++) {
			COLORS.put(PREFIX_COLOR_OPTION + (i + 1), Integer.parseInt(colors[i], 16));
		}
	}

	@Override
	public void input(String... input) {
		boolean gridChange = gridData.updateGrid(input); 
		if (gridChange) {
			String[][] data = generatePlayerView();
			ui.updateUI(data);
		}
	}
	
	/**
	 * Project's entry point
	 * @param args Arguments for the program.
	 */
	public static void main(String[] args) {
		BejeweledMechanics mechanics = new BejeweledMechanics();
		BejeweledUI ui = new BejeweledUI();
		mechanics.attachUI(ui);
		BejeweledGrid grid = new BejeweledGrid();
		mechanics.attachGrid(grid);
	}
}