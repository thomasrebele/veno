package veno.model.treetable;

import org.netbeans.swing.outline.RowModel;

public class StringRowModel implements RowModel {

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueFor(Object node, int column) {
		return node;
	}

	@Override
	public Class getColumnClass(int column) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return false;
	}

	@Override
	public void setValueFor(Object node, int column, Object value) {
		
	}

	@Override
	public String getColumnName(int column) {
		return "Name";
	}
	
}