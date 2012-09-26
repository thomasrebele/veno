package veno.model.treetable.aggregated;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.netbeans.swing.outline.RowModel;

import veno.model.music.Piece;

public class AggregatedRowModel implements RowModel {
	private Object cast(Object o) {
		Object obj = AggregatedTreeModelItem.unpack(o);
		return obj;
	}
	
	private Map<Class<?>, RowModel> map;
	private int columnCount = 0;

	public AggregatedRowModel() {
		map = new HashMap<Class<?>, RowModel>();
	}
	
	public void addModel(Class<?> c, RowModel model) {
		map.put(c, model);
		int ccount = model.getColumnCount();
		if(ccount > columnCount) columnCount = ccount; 
	}
	
	private RowModel getModel(Object o) {
		return map.get(cast(o).getClass());
	}
	
	@Override
	public int getColumnCount() {
		return columnCount;
	}

	@Override
	public Object getValueFor(Object node, int column) {
		RowModel m = getModel(node);
		if(m == null) return "";
		if(column > m.getColumnCount()) return "";
		return m.getValueFor(cast(node), column);
	}

	@Override
	public Class<String> getColumnClass(int column) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(Object node, int column) {
		RowModel m = getModel(node);
		if(m == null) return false;
		if(column > m.getColumnCount()) return false;
		return m.isCellEditable(cast(node), column);
	}

	@Override
	public void setValueFor(Object node, int column, Object value) {
		RowModel m = getModel(node);
		if(m == null) return;
		if(column > m.getColumnCount()) return;
		m.setValueFor(cast(node), column, value);
	}

	@Override
	public String getColumnName(int column) {
		return "AggRowModel";
	}
	
}