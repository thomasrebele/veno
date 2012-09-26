package veno.model.treetable;

import java.io.File;

import org.netbeans.swing.outline.RowModel;

import veno.model.music.Piece;
import veno.model.treetable.aggregated.AggregatedTreeModelItem;

public class PieceRowModel implements RowModel {
	private Piece cast(Object o) {
		if(o instanceof Piece) {
			return (Piece)o;
		}
		return new Piece();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueFor(Object node, int column) {
		Piece p = cast(node);
		if(p instanceof Piece) {
			return p.getTitle();
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