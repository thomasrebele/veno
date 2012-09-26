package veno.gui.frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.RowModel;

import veno.Main;
import veno.database.DatabaseController;
import veno.gui.component.PropertyContainerPanel;
import veno.gui.component.general.ScrolledTreeTablePanel;
import veno.gui.icon.IconLoader;
import veno.model.music.Part;
import veno.model.music.Piece;
import veno.model.property.AbstractPropertyContainer;
import veno.model.property.PiecePropertyContainer;
import veno.model.treetable.ListTreeModel;
import veno.model.treetable.PartRowModel;
import veno.model.treetable.PieceListTreeModel;
import veno.model.treetable.PieceRowModel;
import veno.model.treetable.aggregated.AggregatedRowModel;
import veno.model.treetable.aggregated.AggregatedTreeModelItem;
import veno.model.treetable.file.FileDataProvider;
import veno.model.treetable.file.FileRowModel;
import veno.model.treetable.file.FileTreeModel;
import veno.model.treetable.string.StringDataProvider;
import veno.model.treetable.string.StringRowModel;



public class MainWindow extends JFrame{
	private static final long serialVersionUID = 2740437090361841747L;
	private DatabaseController dbc;
	ScrolledTreeTablePanel treetable;
	PropertyContainerPanel pieceTab = new PropertyContainerPanel();

	public MainWindow() {
		super();
		dbc = Main.getDatabaseController();
		
		this.setTitle("Veno");
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		initMenu();
		
		// SplitPane
		JSplitPane splitPane = new JSplitPane(
				JSplitPane.HORIZONTAL_SPLIT);
		
		splitPane.setDividerSize(10);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		
		splitPane.setLeftComponent(initOverview());
		splitPane.setRightComponent(initTabs());
		
		this.setContentPane(splitPane);
		splitPane.setDividerLocation((int)(0.33*this.getWidth()));
		this.setVisible(true);
		
	}
	
	private void initMenu() {
		// Menu
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Datei");
		JMenuItem close = new JMenuItem("Schließen");
		menubar.add(menu);
		menu.add(close);
		
		this.setJMenuBar(menubar);
	}
	
	private JPanel initOverview() {
		// Overview
		JPanel overview = new JPanel();
		overview.setLayout(new BoxLayout(overview, BoxLayout.Y_AXIS));
		
		JPanel overviewTools = new JPanel();
		overviewTools.setLayout(new BoxLayout(overviewTools, BoxLayout.X_AXIS));
		
		overview.add(overviewTools);
		initOverviewToolbar(overviewTools);
		
		treetable = new ScrolledTreeTablePanel();
		treetable.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				AggregatedTreeModelItem item = (AggregatedTreeModelItem)treetable.getSelectedObject();
				Object o = item.unpack();
				if(o instanceof Piece) {
					PiecePropertyContainer piecepc = dbc.getPieceInfo((Piece)o);
					piecepc.setEditable(PiecePropertyContainer.Editable.TRUE);
					pieceTab.showPropertyContainer(piecepc);
				}
				else {
					pieceTab.showPropertyContainer(null);
				}
			}
		});
		
		overview.add(treetable);
		populateOverview();
		return overview;
	}
	
	private void initOverviewToolbar(JPanel panel) {
		JButton addPiece = new JButton();
		addPiece.setIcon(IconLoader.getIcon(IconLoader.Icons.ADD));
		addPiece.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Piece piece = new Piece();
				PiecePropertyContainer template = new PiecePropertyContainer(piece, null);
				PropertyContainerDialog dialog = new PropertyContainerDialog(template);
				dialog.setVisible(true);
			}});
		panel.add(addPiece);
		
		JButton editPiece = new JButton();
		editPiece.setIcon(IconLoader.getIcon(IconLoader.Icons.EDIT));
		panel.add(editPiece);
		
		JButton deletePiece = new JButton();
		deletePiece.setIcon(IconLoader.getIcon(IconLoader.Icons.DELETE));
		panel.add(deletePiece);
		
		JButton importButton = new JButton();
		importButton.setIcon(IconLoader.getIcon(IconLoader.Icons.IMPORT));
		panel.add(importButton);
		
		JButton exportButton = new JButton();
		exportButton.setIcon(IconLoader.getIcon(IconLoader.Icons.EXPORT));
		panel.add(exportButton);
	}
	
	private JTabbedPane initTabs() {
		// Tabs
		JTabbedPane tabs = new JTabbedPane();
		tabs.add("Stückinfos", pieceTab);
		
		JPanel partTab = new JPanel();
		tabs.add("Stimmen", partTab);
		return tabs;
	}
	
	private void populateOverview() {
		List<Piece> pieces = dbc.getPieces();
		TreeModel treemodel = new PieceListTreeModel("Stücke", pieces);
		AggregatedRowModel rowmodel = new AggregatedRowModel();
		rowmodel.addModel(Piece.class, new PieceRowModel());
		rowmodel.addModel(Part.class, new PartRowModel());
		treetable.setModel(treemodel, "Bezeichnung");
		treetable.setModel(rowmodel);
		treetable.setModel(new StringDataProvider());
	}
}
