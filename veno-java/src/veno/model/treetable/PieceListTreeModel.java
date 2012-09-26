package veno.model.treetable;

import java.util.List;

import javax.swing.tree.TreeModel;

import veno.model.music.Part;
import veno.model.music.Piece;
import veno.model.treetable.aggregated.AggregatedTreeModel;
import veno.model.treetable.aggregated.AggregatedTreeModelItem;

public class PieceListTreeModel extends AggregatedTreeModel {

	public PieceListTreeModel(String name, List<Piece> list) {
		mountTreeModel(null, new ListTreeModel<Piece>(name, list));
		
		Object root = getRoot();
		for(int i=0; i<getChildCount(root); i++) {
			AggregatedTreeModelItem item = (AggregatedTreeModelItem) getChild(root, i);
			Piece p = (Piece) item.object;
			List<Part> parts = p.getParts();
			if(parts.size() > 0)
				mountTreeModel(item, new ListTreeModel<Part>(p, p.getParts()));
		}
	}

}