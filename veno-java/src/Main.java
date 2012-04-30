import javax.swing.JFrame;
import javax.swing.UIManager;

import gui.MainWindow;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (Exception e1) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e2) {
			}
		}
		JFrame main = new MainWindow();

	}

}
