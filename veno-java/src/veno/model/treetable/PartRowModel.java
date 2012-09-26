package veno.model.treetable;

import java.io.File;

import org.netbeans.swing.outline.RowModel;

import veno.model.music.Part;
import veno.model.treetable.aggregated.AggregatedTreeModelItem;

public class PartRowModel implements RowModel {
	private Part cast(Object o) {
		if(o instanceof Part) {
			return (Part)o;
		}
		return new Part();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueFor(Object node, int column) {
		Part p = cast(node);
		if(p instanceof Part) {
			return "Part: " + p.toString();
		}
		return node;
	}

	@Override
	public Class getColumnClass(int column) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		return true;
	}

	@Override
	public void setValueFor(Object node, int column, Object value) {
		
	}

	@Override
	public String getColumnName(int column) {
		return "Titel";
	}
	
}