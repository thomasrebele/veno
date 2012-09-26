package veno;
import javax.swing.UIManager;
import veno.database.DatabaseController;
import veno.gui.frame.MainWindow;
import veno.model.music.Piece;
import veno.model.property.PropertyValue;




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
			System.out.println("p: " + p.getId() + " " + p.getTitle() + " " + p.getCreateDate());
			for(PropertyValue ppv : dbc.getPieceInfo(p)) {
				if(ppv == null) {System.out.println("\tnull"); continue;}
				System.out.println("\tppv: " + ppv.getName() + " = " + ppv.getValue());
			}
		}
		
		//@SuppressWarnings("unused")
		MainWindow main = new MainWindow();
		//JFrame importWindow = new ImportWindow();
		
	}
	
	public static DatabaseController getDatabaseController() {
		return dbc;
	}

}
