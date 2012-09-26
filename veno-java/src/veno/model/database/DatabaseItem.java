package veno.model.database;

import java.util.Date;

public class DatabaseItem {

	protected Date createDate;
	protected int id;
	
	public DatabaseItem() {
		createDate = new Date();
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date date) {
		this.createDate = date;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
