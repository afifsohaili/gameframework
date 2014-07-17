package se350.afifsohaili.sudoku;

import java.util.ArrayList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se350.afifsohaili.gameframework.Mechanics;
import se350.afifsohaili.gameframework.UserInterface;

/**
 * @author Afif Sohaili
 */
public class SudokuUI implements UserInterface {
	private static final String ERROR_WINDOW_TITLE = "Error encountered!";
	private static final String WINDOW_TITLE = "Sudoku";
	private static final String CHECK_SOLUTION_BTN = "Check solution!";
	private static final String WRONG_INPUT_FORMAT = "Wrong input format or incomplete Sudoku grid!";
	private static final String CONGRATE_MSG = "Congratulations, you have won!";
	private static final String WRONG_ANSWER = "Awwww. The answer you gave was wrong.";
	private static final int FONT_SIZE = 32;
	private static final int DIMENSION = 60;
	private static final String RESET_BTN = "Reset";
	private JTextField[][] textFields;
	private JFrame frame;
	
	@Override
	public void displayError(String errorMsg) {
		JOptionPane.showMessageDialog(null, errorMsg, ERROR_WINDOW_TITLE, JOptionPane.ERROR_MESSAGE, null);
	}

	@Override
	public void run(final Mechanics mechanics) {
		textFields = new JTextField[Mechanics.getVerticalTiles()][Mechanics.getHorizontalTiles()];
		for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				textFields[row][column] = null;
			}
		}
		// jframe
		final JFrame frame = new JFrame(WINDOW_TITLE);
		JPanel grid = new JPanel(new GridLayout(Mechanics.getHorizontalTiles() / 3, Mechanics.getVerticalTiles() / 3));
		ArrayList<JPanel> boxes = new ArrayList<JPanel>();
		for (int i = 0; i < Mechanics.getHorizontalTiles() / 3 * Mechanics.getVerticalTiles() / 3; i++) {
			boxes.add(new JPanel(new GridLayout(Mechanics.getHorizontalTiles() / 3, Mechanics.getVerticalTiles() / 3)));
		}
		// add textfields
		for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				JTextField textField = new JTextField();
				textField.setFont(new Font("Arial", Font.PLAIN, FONT_SIZE));
				textField.setHorizontalAlignment(JTextField.CENTER);
				textField.setPreferredSize(new Dimension(DIMENSION, DIMENSION));
				textField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
				textFields[row][column] = textField;
				boxes.get(((row / 3) * 3) + (column / 3)).add(textField);
			}
		}
		
		for (JPanel box : boxes) {
			box.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			grid.add(box);
		}
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(grid, BorderLayout.PAGE_START);
		JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
		JButton checkSolution = new JButton(CHECK_SOLUTION_BTN);
		checkSolution.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean wrongInputFormat = false;
				for (int i = 0; i < textFields.length && !wrongInputFormat; i++) {
					for (int j = 0; j < textFields[i].length && !wrongInputFormat; j++) {
						if (!textFields[i][j].getText().matches("[1-9][0-9]{0,8}")) {
							displayError(WRONG_INPUT_FORMAT);
							wrongInputFormat = true;
							break;
						}
					}
				}
				if (!wrongInputFormat) {
					SudokuData[][] number = new SudokuData[Mechanics.getVerticalTiles()][Mechanics.getHorizontalTiles()];
					for (int i = 0; i < textFields.length; i++) {
						for (int j = 0; j < textFields[i].length; j++) {
							number[i][j] = new SudokuData(Integer.parseInt(textFields[i][j].getText()));
						}
					}
					if (mechanics.checkWinCondition(number)) {
						congratulate();
						exit();
					}
					else {
						displayError(WRONG_ANSWER);
					}
				}
			}
		});
		JButton reset = new JButton(RESET_BTN);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 0; i < textFields.length; i++) {
					for (int j = 0; j < textFields.length; j++) {
						if (textFields[i][j].isEditable()) {
							textFields[i][j].setText("");
						}
					}
				}
			}
		});
		buttonPanel.add(checkSolution);
		buttonPanel.add(reset);
		panel.add(buttonPanel);
		frame.getContentPane().add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.frame = frame;
	}
	
	@Override
	public void updateUI(String[][] data) {
		for (int row = 0; row < Mechanics.getVerticalTiles(); row++) {
			for (int column = 0; column < Mechanics.getHorizontalTiles(); column++) {
				if (data[row][column] != null) {
					textFields[row][column].setText(data[row][column]);
					textFields[row][column].setEditable(false);
				}
			}
		}
	}
	
	@Override
	public void exit() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}
	
	/**
	 * Congratulate the player if he has solved the Sudoku puzzle.
	 * @param frame 
	 */
	public void congratulate() {
		JOptionPane.showMessageDialog(null, CONGRATE_MSG);
	}
}
