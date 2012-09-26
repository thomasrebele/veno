package veno.gui.component.general;

import java.awt.Color;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;
import org.netbeans.swing.outline.RenderDataProvider;
import org.netbeans.swing.outline.RowModel;

import veno.model.treetable.aggregated.AggregatedTreeModel;
import veno.model.treetable.aggregated.AggregatedTreeModelItem;
import veno.model.treetable.file.FileTreeModel;
import veno.model.treetable.string.StringDataProvider;
import veno.model.treetable.string.StringRowModel;

public class ScrolledTreeTablePanel extends JPanel {
	private static final long serialVersionUID = 7342160763603964852L;
	
	Outline outline;

	private String treemodelColName = "Nodes";
	private TreeModel treemodel;
	private RowModel rowmodel;
	private RenderDataProvider dataprovider;

	public ScrolledTreeTablePanel() {
		outline = new Outline();
		outline.setRootVisible (false);
		
		JScrollPane scroll = new ScrollPane(outline, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(scroll);
	}
	
	public void setModel(TreeModel treemodel, String colName) {
		this.treemodel = treemodel;
		this.treemodelColName = colName;
		actualize();
	}
	
	public void setModel(RowModel rowmodel) {
		this.rowmodel = rowmodel;
		actualize();
	}
	
	public void setModel(RenderDataProvider dataprovider) {
		this.dataprovider = dataprovider;
		actualize();
	}
	
	private void actualize() {
		if(treemodel != null && rowmodel != null) { 
			OutlineModel model = DefaultOutlineModel.createOutlineModel(treemodel, 
					rowmodel, true, treemodelColName);
			outline.setModel (model);
			
		}
		
		if(dataprovider != null) {
			outline.setRenderDataProvider(dataprovider); 
		}
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		outline.getSelectionModel().addListSelectionListener(listener);
	}
	
	public int getSelectedRow() {
		return outline.getSelectedRow();
	}
	
	public int[] getSelectedRows() {
		return outline.getSelectedRows();
	}
	
	public Object getSelectedObject() {
		return outline.getValueAt(getSelectedRow(), 0);
	}
	
	public Object[] getSelectedObjects() {
		int selectedRows[] = getSelectedRows();
		Object result[] = new Object[selectedRows.length];
		
		for(int i=0; i<selectedRows.length; i++) {
			result[i] = outline.getValueAt(selectedRows[i], 0);
		}
		return result;
	}
}