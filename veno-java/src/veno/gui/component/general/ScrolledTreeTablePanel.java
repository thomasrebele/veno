package veno.gui.component;

import java.awt.Color;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;
import org.netbeans.swing.outline.RenderDataProvider;
import org.netbeans.swing.outline.RowModel;

import veno.model.treetable.AggregatedTreeModel;
import veno.model.treetable.AggregatedTreeModelItem;
import veno.model.treetable.FileTreeModel;
import veno.model.treetable.StringDataProvider;
import veno.model.treetable.StringRowModel;

public class ScrolledTreeTablePanel extends JPanel {
	private static final long serialVersionUID = 7342160763603964852L;
	
	Outline outline;

	private TreeModel treemodel;
	private RowModel rowmodel;
	private RenderDataProvider dataprovider;

	public ScrolledTreeTablePanel() {
		/*
		TreeModel treeMdl = new FileTreeModel (new File("/"));

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		DefaultMutableTreeNode child1 = new DefaultMutableTreeNode("Child 1");
		root.add(child1);
		DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Child 2");
		root.add(child2);
		TreeModel model = new DefaultTreeModel(root);
		
		AggregatedTreeModel aggMdl = new AggregatedTreeModel();
		aggMdl.mountTreeModel(null, model);
		//aggMdl.mountTreeModel((AggregatedTreeModelItem)aggMdl.getChild(null,0), model);
		//aggMdl.mountTreeModel((AggregatedTreeModelItem)aggMdl.getChild(null,1), model);
		aggMdl.addChildTreeModel((AggregatedTreeModelItem)aggMdl.getRoot(), model);
		aggMdl.addChildTreeModel((AggregatedTreeModelItem)aggMdl.getRoot(), model);
		aggMdl.addChildTreeModel((AggregatedTreeModelItem)aggMdl.getRoot(), model);
		//aggMdl.addChildTreeModel((AggregatedTreeModelItem)aggMdl.getChild(aggMdl.getChild(null, 0),0), treeMdl);
		aggMdl.printDebugInfo();
		Object aggRoot = aggMdl.getRoot();
		System.out.println(aggMdl.getChildCount(null));
		//System.exit(0);
		*/

		outline = new Outline();
		outline.setRootVisible (false);
		
		JScrollPane scroll = new JScrollPane();
		scroll.getHorizontalScrollBar().setUnitIncrement(10);
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.getViewport().add(outline);

		scroll.setBackground(Color.BLUE);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(scroll);
	}
	
	public void setModel(TreeModel treemodel) {
		this.treemodel = treemodel;
		actualize();
	}
	
	public void setModel(TreeModel treemodel, RowModel rowmodel, RenderDataProvider dataprovider) {
		this.treemodel = treemodel;
		this.rowmodel = rowmodel;
		this.dataprovider = dataprovider;
		actualize();
	}
	
	private void actualize() {
		if(treemodel != null && rowmodel != null) { 
			OutlineModel mdl = DefaultOutlineModel.createOutlineModel(treemodel, 
					new StringRowModel(), true);
			outline.setModel (mdl);
		}
		
		if(dataprovider != null) {
			outline.setRenderDataProvider(dataprovider); 
		}
	}
}