package se350.afifsohaili.gameframework.uifactory;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * @author Afif Sohaili
 */
public class GridPanel extends JPanel {
	private static final long serialVersionUID = 7624220260690852121L;
	SelectableTile selectedTile;
	
	public GridPanel(int horizontalTiles, int verticalTiles) {
		super(new GridLayout(verticalTiles, horizontalTiles));
		selectedTile = null;
	}
	
}