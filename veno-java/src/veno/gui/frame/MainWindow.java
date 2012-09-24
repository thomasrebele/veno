package veno.gui.main;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;
import org.netbeans.swing.outline.RenderDataProvider;
import org.netbeans.swing.outline.RowModel;

import veno.database.DatabaseController;
import veno.gui.component.ScrolledTreeTablePanel;
import veno.model.treetable.FileDataProvider;
import veno.model.treetable.FileRowModel;
import veno.model.treetable.FileTreeModel;



public class MainWindow extends JFrame{
	private static final long serialVersionUID = 2740437090361841747L;
	private DatabaseController dbc;

	public MainWindow() {
		super();
		
		this.setTitle("Veno");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Split Panel
		//JPanel panel1 = new JPanel();
		//panel1.add(new JButton("test"));
		
		ScrolledTreeTablePanel overview = new ScrolledTreeTablePanel();
		overview.setModel(new FileTreeModel(new File("/")), new FileRowModel(), new FileDataProvider());
		//panel1.add(overview);
		JPanel panel2 = new JPanel();
		panel2.add(new JButton("test2"));

		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT, overview, panel2);
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		
		JPanel p1 = (JPanel) splitPane.getComponent(1);
		p1.setBackground(Color.white);
		
		// Menu
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Datei");
		JMenuItem close = new JMenuItem("Schließen");
		menubar.add(menu);
		menu.add(close);
		
		this.setJMenuBar(menubar);
		
		this.setContentPane(splitPane);
		//this.add(overview);
		this.setVisible(true);
		
	}

	public void setDatabaseController(DatabaseController dbc) {
		this.dbc = dbc;
	}
}
