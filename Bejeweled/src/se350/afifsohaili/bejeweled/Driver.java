package se350.afifsohaili.bejeweled;

/**
 * @author Afif Sohaili
 */
public class Driver {
	public static void main(String[] args) {
		BejeweledMechanics mechanics = new BejeweledMechanics();
		BejeweledUI ui = new BejeweledUI();
		mechanics.attachUI(ui);
		BejeweledGrid grid = new BejeweledGrid();
		mechanics.attachGrid(grid);
	}
}
