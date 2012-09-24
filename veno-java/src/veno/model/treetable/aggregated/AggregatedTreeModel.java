package veno.model.treetable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeModel;

public class AggregatedTreeModel implements TreeModel {
	private AggregatedTreeModelItem root;
	private static long identifierCounter = 1;
	private Map<AggregatedTreeModelItem, List<AggregatedTreeModelItem>> childTreeModels;
	private Map<AggregatedTreeModelItem, TreeModel> mountPoints;

	public AggregatedTreeModel() {
		this.root = new AggregatedTreeModelItem();
		childTreeModels = new HashMap<AggregatedTreeModelItem, List<AggregatedTreeModelItem>>();
		mountPoints = new HashMap<AggregatedTreeModelItem, TreeModel>();
	}
	
	public void mountTreeModel(AggregatedTreeModelItem parent, TreeModel model) {
		if(parent == null) parent = root;
		if(model == null) {
			mountPoints.remove(parent);
		}
		else {
			mountPoints.put(parent, model);
		}
	}
	
	public void printDebugInfo() {
		for(AggregatedTreeModelItem item : mountPoints.keySet()) {
			System.out.println(item + " mounted to " + mountPoints.get(item));
		}
	}
	
	public void addChildTreeModel(AggregatedTreeModelItem parent, TreeModel model) {
		if(parent == null) parent = root;
		List<AggregatedTreeModelItem> childTreeModelList = childTreeModels.get(parent);
		if(childTreeModelList == null) {
			childTreeModelList = new ArrayList<AggregatedTreeModelItem>();
			childTreeModels.put(parent, childTreeModelList);
		}
		childTreeModelList.add(new AggregatedTreeModelItem(model.getRoot(),model,identifierCounter++));
	}

	@Override
	public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
		//do nothing
	}

	@Override
	public Object getChild(Object parent, int index) {
		if(parent == null) parent = root;
		if(!(parent instanceof AggregatedTreeModelItem)) {
			System.out.println("Wrong type in AggregatedTreeModel");
			throw new NullPointerException();
		}
		AggregatedTreeModelItem item = (AggregatedTreeModelItem) parent;
		TreeModel mount = mountPoints.get(parent);
		if(mount != null) {
			
			return new AggregatedTreeModelItem(mount.getChild(mount.getRoot(), index), mount);
		}
		else {
			List<AggregatedTreeModelItem> mountPointList = childTreeModels.get(parent);
			int count;
			if(mountPointList == null) {
				count = 0;
			}
			else {
				count = mountPointList.size();
			}
			if(index < count) {
				AggregatedTreeModelItem model = mountPointList.get(index);
				return model;
			}
			else {
				TreeModel model = item.model;
				return new AggregatedTreeModelItem(model.getChild(item.object, index-count), model);
			}
		}
	}

	@Override
	public int getChildCount(Object parent) {
		if(parent == null) parent = root;
		if(!(parent instanceof AggregatedTreeModelItem)) {
			System.out.println("Wrong type in AggregatedTreeModel");
			throw new NullPointerException();
		}
		AggregatedTreeModelItem item = (AggregatedTreeModelItem) parent;
		TreeModel mount = mountPoints.get(parent);
		if(mount != null) {
			return mount.getChildCount(mount.getRoot());
		}
		else {
			List<AggregatedTreeModelItem> mountPointList = childTreeModels.get(parent);
			int childCount = 0;
			if(item.model != null) {
				childCount = item.model.getChildCount(item.object);
			}
			if(mountPointList == null) {
				return childCount;
			}
			else {
				return mountPointList.size() + childCount;
			}
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if(!(parent instanceof AggregatedTreeModelItem) || !(child instanceof AggregatedTreeModelItem)) {
			System.out.println("Wrong type in AggregatedTreeModel");
			throw new NullPointerException();
		}
		AggregatedTreeModelItem item = (AggregatedTreeModelItem) parent;
		AggregatedTreeModelItem childItem = (AggregatedTreeModelItem) child;
		TreeModel mount = mountPoints.get(parent);
		if(mount != null) {
			return mount.getIndexOfChild(mount.getRoot(), childItem.object);
		}
		else {
			List<AggregatedTreeModelItem> mountPointList = childTreeModels.get(parent);
			if(mountPointList == null) {
				return item.model.getIndexOfChild(item.object, childItem.object);
			}
			else {
				int index = mountPointList.indexOf(child);
				if(index < 0) {
					index = item.model.getIndexOfChild(item.object, childItem.object) + mountPointList.size();
				}
				return index;
			}
		}
	}

	@Override
	public Object getRoot() {
		TreeModel mount = mountPoints.get(root);
		if(mount != null) {
			return new AggregatedTreeModelItem(mount.getRoot(), mount, 0);
		}
		else {
			return root;
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		if(!(node instanceof AggregatedTreeModelItem)) {
			System.out.println("Wrong type in AggregatedTreeModel");
			throw new NullPointerException();
		}
		AggregatedTreeModelItem item = (AggregatedTreeModelItem) node;
		TreeModel mount = mountPoints.get(node);
		if(mount != null) {
			return mount.isLeaf(mount.getRoot());
		}
		else {
			List<AggregatedTreeModelItem> mountPointList = childTreeModels.get(node);
			if(mountPointList == null) {
				return item.model.isLeaf(item.object);
			}
			else {
				return mountPointList.size() == 0 && item.model.isLeaf(item.object);
			}
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


/*private void testAgg(AggregatedTreeModel model, AggregatedTreeModelItem root, int depth) {
	depth++;
	if(depth>4) return;
	for(int i=0; i<model.getChildCount(root); i++) {
		AggregatedTreeModelItem item = (AggregatedTreeModelItem)model.getChild(root, i);
		System.out.println(depth + ": " + item);
		testAgg(model, item, depth);
	}
}*/