package veno.model.music;

import java.util.Date;
import java.util.List;

import veno.Main;
import veno.model.database.DatabaseItem;

public class Piece extends DatabaseItem {
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return title;
	}
	
	public List<Part> getParts() {
		return Main.getDatabaseController().getParts(id);
	}
}
