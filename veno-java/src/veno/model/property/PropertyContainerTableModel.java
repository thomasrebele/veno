package veno.model.property;

import javax.swing.table.AbstractTableModel;

public class PropertyContainerTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 7302692404786494382L;
	private AbstractPropertyContainer container;
	
	public PropertyContainerTableModel(AbstractPropertyContainer container) {
		this.container = container;
	}
	
	public void setContainer(AbstractPropertyContainer container) {
		this.container = container;
	}
	
	@Override
	public int getColumnCount() {
		return 2;
	}
	
	@Override
	public String getColumnName(int col) {
		if(col == 0) return "Name";
		if(col == 1) return "Value";
		return "???";
	}

	@Override
	public int getRowCount() {
		if(container == null) return 0;
		return container.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		if(container == null) return null;
		if(col == 0) return container.get(row).name;
		if(col == 1) return container.get(row).value;
		return "???";
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if(col == 0 || container == null) return false;
		return container.isEditable(row);
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		if(value instanceof String) {
			String str = (String)value;
			if(str.equals(getValueAt(row, col))) return;
			container.set(str, row);
			fireTableCellUpdated(row, col);
		}
	}
}
