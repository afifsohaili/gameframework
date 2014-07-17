package se350.afifsohaili.gameframework.uifactory;

import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import se350.afifsohaili.gameframework.Mechanics;
import se350.afifsohaili.gameframework.UserInterface;

/**
 * @author Afif Sohaili
 */
public class Window extends JFrame implements UserInterface {
	private static final long serialVersionUID = 2012557121165128769L;
	private GridPanel grid;
	private String errorWindowTitle;
	private String errorMessage;
	
	public Window(String windowTitle) {
		super(windowTitle);
	}

	@Override
	public void run(Mechanics mechanics) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		setVisible(true);
	}

	@Override
	public void updateUI(String[][] data) {
		grid.updateUI();
	}

	@Override
	public void displayError(String errorMsg) {
		JOptionPane.showMessageDialog(null, errorWindowTitle, errorMessage, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void exit() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
}
