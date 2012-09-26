package veno.model.treetable.aggregated;

import javax.swing.tree.TreeModel;

public class AggregatedTreeModelItem {
	public Object object;
	public TreeModel model;
	private long identifier;
	
	public AggregatedTreeModelItem() {
	}
	
	public AggregatedTreeModelItem(long identifier) {
		this.identifier = identifier;
	}
	
	public AggregatedTreeModelItem(Object object, TreeModel model) {
		this.object = object;
		this.model = model;
	}
	
	public AggregatedTreeModelItem(Object object, TreeModel model, long identifier) {
		this(object, model);
		this.identifier = identifier;
	}
	
	public Object unpack() {
		Object item = object;
		while(item instanceof AggregatedTreeModelItem) {
			item = ((AggregatedTreeModelItem) item).object;
		}
		return item;
	}
	
	public static Object unpack(Object item) {
		Object o = item;
		if(o instanceof AggregatedTreeModelItem) {
			o = ((AggregatedTreeModelItem) o).unpack();
		}
		return o;
	}
	
	public String toString() {
		Object content = unpack();
		if(content == null) 
			return "ATMI: null";
		else
			return "ATMI: " + unpack().toString();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null) return false;
		if(!(o instanceof AggregatedTreeModelItem)) return false;
		AggregatedTreeModelItem item = (AggregatedTreeModelItem) o;
		boolean objectEq = object == null ? 
				object == item.object : object.equals(item.object);
		boolean modelEq = model == null ?
				model == item.model : model.equals(item.model);
		boolean identifierEq = identifier == item.identifier;
		return objectEq && modelEq && identifierEq;
	}
	
	@Override
	public int hashCode() {
		
		return (object == null ? 0 : object.hashCode()) + (model == null ? 0 : model.hashCode());
	}
}