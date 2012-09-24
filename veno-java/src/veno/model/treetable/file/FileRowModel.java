package veno.model.treetable;


import java.io.File;
import java.util.Date;

import org.netbeans.swing.outline.RowModel;

public class FileRowModel implements RowModel {
	private File cast(Object o) {
		File f = (File)((AggregatedTreeModelItem) o).unpack();
		if(f==null) return new File("/");
		return f;
	}
	
	public Class getColumnClass(int column) {
		switch (column) {
			case 0 : return Date.class;
			case 1 : return Long.class;
			default : assert false;
		}
		return null;
	}
	
	public int getColumnCount() {
		return 2;
	}
	
	public String getColumnName(int column) {
		return column == 0 ? "Date" : "Size";
	}
	
	public Object getValueFor(Object node, int column) {
		System.out.println(node.getClass());
		File f = cast(node);
		switch (column) {
			case 0 : return new Date (f.lastModified());
			case 1 : return new Long (f.length());
			default : assert false;
		}
		return null;
	}
	
	public boolean isCellEditable(Object node, int column) {
		return false;
	}
	
	public void setValueFor(Object node, int column, Object value) {
		//do nothing, nothing is editable
	}
 }