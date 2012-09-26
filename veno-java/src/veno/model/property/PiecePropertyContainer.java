package veno.model.property;

import java.util.Iterator;
import java.util.List;

import veno.model.music.Piece;

public class PiecePropertyContainer extends AbstractPropertyContainer {
	private Piece piece;
	
	public PiecePropertyContainer(Piece piece, List<PropertyValue> list) {
		super(list);
		this.piece = piece;
	}
	
	@Override
	protected PropertyValue getBaseValue(int index) {
		switch (index) {
		case 0: return createBV("ID", ""+piece.getId());
		case 1: return createBV("Titel", ""+piece.getTitle());
		case 2: return createBV("Erstellt", ""+piece.getCreateDate());
		}
		return null;
	}

	@Override
	protected int getBaseValueCount() {
		return 3;
	}
	
	@Override
	protected Editable isEditableBaseValue(int index) {
		if(index == 0) return Editable.FALSE;
		if(index == 2) return Editable.FALSE;
		return null;
	}

	@Override
	protected void setBaseValue(String str, int index) {
		switch (index) {
		case 1: piece.setTitle(str);
		case 2: // TODO
		}
		return;
	}
}
