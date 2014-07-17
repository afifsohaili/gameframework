package se350.afifsohaili.gameframework.uifactory;

/**
 * @author Afif Sohaili
 */
public abstract class SelectableTile implements Tile {
	private boolean selected;
	
	public SelectableTile(Tile tile) {
		selected = false;
	}
	
	public boolean isSelected() {
		return new Boolean(selected);
	}
	
	public void select() {
		selected = true;
	}
	
	public void deselect() {
		selected = false;
	}
}
