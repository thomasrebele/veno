package veno.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import veno.model.music.Piece;

public class DatabaseController {

	private SQLiteDatabase db;
	
	public DatabaseController() {
		
	}
	
	public void init() {
		try {
			db = new SQLiteDatabase();
	
			createTables();
			// bug in datetime(now)? (wrong hour)
			db.statement.executeUpdate("insert into piece values(1, 'leo', datetime('now'))");
			db.statement.executeUpdate("insert into piece_info values(1,1,'test')");
			
	
			ResultSet rs = db.statement.executeQuery("select * from property");
			while(rs.next())
			{
				// read the result set
				System.out.print("property: name = " + rs.getString("name") + ", ");
				System.out.println("id = " + rs.getInt("id") + "");
			}
			
			
			rs = db.statement.executeQuery("select * from piece");
			while(rs.next())
			{
				// read the result set
				System.out.print("piece: name = " + rs.getString("title") + ", ");
				System.out.print("id = " + rs.getInt("id") + ", ");
				System.out.println("created = " + rs.getString("created"));
			}
			
	
			rs = db.statement.executeQuery("select * from piece_info");
			while(rs.next())
			{
				// read the result set
				System.out.print("piece_info: name = " + rs.getString("id") + ", ");
				System.out.print("id = " + rs.getInt("property") + ", ");
				System.out.println("created = " + rs.getString("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: show swing message box
		}
	}
	
	public void finalize() {
		db.finalize();
	}
	
	private void createTables() throws SQLException {
		// TODO: remove this lines!!!
		db.statement.execute("drop table if exists piece");
		db.statement.execute("drop table if exists piece_info");
		db.statement.execute("drop table if exists property");
		db.statement.executeUpdate(
			"create table if not exists piece(" +
				"id integer primary key autoincrement, " +
				"title string, " +
				"created datetime" +
			")");
		db.statement.executeUpdate("" +
			"create table if not exists property(" +
				"id integer primary key autoincrement, " +
				"name string" +
			")");
		db.checkOrInsert("property", "name", "'Beschreibung'");
		db.checkOrInsert("property", "name", "'Kommentar'");
		
		db.statement.executeUpdate(
			"create table if not exists piece_info(" + 
				"id integer references piece(id) on delete cascade on update cascade," +
				"property integer references property(id) on delete cascade on update cascade," +
				"value string" +
			")");
	}
	
	public List<Piece> getPieces() {
		System.out.println("getPieces()");
		List<Piece> list = new ArrayList<Piece>();
		ResultSet rs = null;
		try {
			rs = db.statement.executeQuery("select * from piece");
			while(rs.next())
			{
				// read the result set
				Piece piece = new Piece();
				piece.setId(rs.getInt("id"));
				piece.setDate(db.StringToDate(rs.getString("created")));
				piece.setTitle(rs.getString("title"));
				list.add(piece);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		
		return list;
	}
}
