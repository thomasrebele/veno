package veno;
import javax.swing.JFrame;
import javax.swing.UIManager;

import veno.database.DatabaseController;
import veno.database.SQLiteDatabase;
import veno.gui.ImportWindow;
import veno.gui.main.MainWindow;
import veno.model.music.Piece;




public class Main {
	static DatabaseController dbc;

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
		
		dbc = new DatabaseController();
		dbc.init();
		for(Piece p : dbc.getPieces()) {
			System.out.println("p: " + p.getId() + " " + p.getTitle() + " " + p.getDate());
		}
		
		//@SuppressWarnings("unused")
		MainWindow main = new MainWindow();
		main.setDatabaseController(dbc);
		//JFrame importWindow = new ImportWindow();
		
	}

}
