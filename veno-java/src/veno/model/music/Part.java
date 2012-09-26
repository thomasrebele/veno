package veno.model.music;

import java.util.Date;

import veno.model.database.DatabaseItem;

public class Part extends DatabaseItem {
	/*private String instrument;
	private String musicalTuning;
	private String instrumentRank;*/
	private int pieceId;
	
	/*public int getPieceId() {
		return pieceId;
	}*/
	public void setPieceId(int pieceId) {
		this.pieceId = pieceId;
	}
	
	@Override
	public String toString() {
		return "" + id + ", " + pieceId + ", " + createDate;
	}
}
