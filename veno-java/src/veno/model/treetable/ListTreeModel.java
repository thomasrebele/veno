package veno.model.treetable;

import java.util.List;

import javax.swing.tree.TreeModel;

public class ListTreeModel<T> implements TreeModel {

	private List<T> list;
	private Object root;

	/*public ListTreeModel(List<T> list) {
		this.list = list;
	}*/
	
	public ListTreeModel(Object root, List<T> list) {
		this.list = list;
		this.root = root;
	}

	@Override
	public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
		//do nothing
	}

	@Override
	public T getChild(Object parent, int index) {
		if(parent == root)
			return list.get(index);
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if(parent == root) {
			return list.size();
		}
		return 0;
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(parent == root) {
			return list.indexOf(child);
		}
		return -1;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public boolean isLeaf(Object node) {
		if(node == root)
			return false;
		else {
			return true;
		}
	}

	@Override
	public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
		//do nothing
	}

	@Override
	public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
		//do nothing
	}

}