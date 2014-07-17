package se350.afifsohaili.bejeweled;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import se350.afifsohaili.gameframework.Mechanics;
import se350.afifsohaili.gameframework.UserInterface;

/**
 * @author Afif Sohaili
 */
public class BejeweledUI implements UserInterface {
	private final int SELECTED_BORDER_COLOR = Integer.parseInt("000000", 16);
	private static final String WINDOW_TITLE = "Bejeweled";
	private static final int DIMENSION = 60;
	private static final int BORDER = 3;
	private static final int BG_COLOR = Integer.parseInt("CCCCCC", 16);
	private static final String ERROR_WINDOW_TITLE = "Error !";
	private static final int GAP = 3;
	
	private SelectedBlock selection;
	private Block[][] blocks;
	private JFrame frame;
	
	@Override
	public void run(final Mechanics mechanics) {
		blocks = new Block[Mechanics.getVerticalTiles()][Mechanics.getHorizontalTiles()];
		selection = new SelectedBlock();
		// main panel that contains the grid.
		JPanel grid = new JPanel(new GridLayout(Mechanics.getVerticalTiles(), Mechanics.getHorizontalTiles(), GAP, GAP));
		grid.setBackground(new Color(BG_COLOR));
		// generate grid
		for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				final Block block = new Block(DIMENSION, DIMENSION, Color.BLACK, BORDER);
				blocks[row][column] = block;
				final int r = row;
				final int c = column;
				block.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (selection.isSelected()) {
							// prepare input string
							String selectedBlock1 = selection.toString();
							String selectedBlock2 = r + "," + c;
							// send input if the two blocks clicked are not the same.
							if (!selectedBlock1.equals(selectedBlock2)) {
								mechanics.input(selectedBlock1, selectedBlock2);
							}
							// deselect
							selection.deselect();
						}
						else {
							selection.select(r, c);
						}
					}
				});
				grid.add(block);
			}
		}
		final JFrame frame = new JFrame(WINDOW_TITLE);
		frame.getContentPane().add(grid);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.frame = frame;
	}

	@Override
	public void updateUI(String[][] data) {
		for (int row = Mechanics.getVerticalTiles() - 1; row > -1; row--) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				Color color = new Color(Integer.parseInt(data[row][column]));
				blocks[row][column].recolor(color);
			}
		}
	}

	@Override
	public void displayError(String errorMsg) {
		JOptionPane.showMessageDialog(null, errorMsg, ERROR_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	private class SelectedBlock {
		private int row, column;
		
		void select(int row, int column) {
			this.row = row;
			this.column = column;
			selectGUI();
		}
		
		void deselect() {
			deselectGUI();
			row = -1;
			column = -1;
		}
		
		private void selectGUI() {
			Block block = blocks[row][column];
			block.setBorder(BorderFactory.createLineBorder(new Color(SELECTED_BORDER_COLOR), BORDER * 2));
		}

		private void deselectGUI() {
			Block block = blocks[row][column];
			block.setBorder(null);
		}

		boolean isSelected() {
			return (row > -1 && column > -1);
		}
		
		@Override
		public String toString() {
			return row + "," + column;
		}
	}
}
