package veno.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import veno.model.music.Part;
import veno.model.music.Piece;
import veno.model.property.PiecePropertyContainer;
import veno.model.property.PropertyValue;

public class DatabaseController {

	private SQLiteDatabase db;
	
	public DatabaseController() {
		
	}
	
	public void init() {
		try {
			db = new SQLiteDatabase();
	
			createTables();
			// bug in datetime(now)? (wrong hour)
			db.statement.executeUpdate("insert into piece values(1, 'Marsch', datetime('now'))");
			db.statement.executeUpdate("insert into piece values(2, 'Polka', datetime('now'))");
			db.statement.executeUpdate("insert into piece_info values(1,1,1,'test')");
			
			db.statement.executeUpdate("insert into part values(1, 1, datetime('now'))");
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
		db.statement.execute("drop table if exists part");
		db.statement.execute("drop table if exists piece_info");
		db.statement.execute("drop table if exists property");
		db.statement.execute("drop table if exists piece");

		// TODO: automatically generate tables from model
		db.statement.executeUpdate("" +
			"create table if not exists property(" +
				"id integer primary key autoincrement, " +
				"name string" +
			")");
		db.checkOrInsert("property", "name", "'Beschreibung'");
		db.checkOrInsert("property", "name", "'Kommentar'");
		
		db.statement.executeUpdate(
			"create table if not exists piece(" +
				"id integer primary key autoincrement, " +
				"title string, " +
				"created datetime" +
			")");
		
		db.statement.executeUpdate(
			"create table if not exists piece_info(" + 
				"id integer primary key autoincrement, " +
				"piece integer references piece(id) on delete cascade on update cascade," +
				"property integer references property(id) on delete cascade on update cascade," +
				"value string" +
			")");
		

		db.statement.executeUpdate(
			"create table if not exists part(" +
				"id integer primary key autoincrement, " +
				"piece integer references piece(id) on delete cascade on update cascade," +
				"created datetime" +
			")");
	}
	
	public List<Piece> getPieces() {
		List<Piece> list = new ArrayList<Piece>();
		ResultSet rs = null;
		try {
			rs = db.statement.executeQuery("select * from piece");
			while(rs.next()) {
				// read the result set
				Piece piece = new Piece();
				piece.setId(rs.getInt("id"));
				piece.setCreateDate(db.StringToDate(rs.getString("created")));
				piece.setTitle(rs.getString("title"));
				list.add(piece);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	public Piece getPiece(int id) {
		ResultSet rs = null;
		try {
			rs = db.statement.executeQuery("select * from piece where id = " + id);
			if(rs.next()) {
				// read the result set
				Piece piece = new Piece();
				piece.setId(rs.getInt("id"));
				piece.setCreateDate(db.StringToDate(rs.getString("created")));
				piece.setTitle(rs.getString("title"));
				return piece;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	
	public PiecePropertyContainer getPieceInfo(Piece piece) {
		List<PropertyValue> list = new ArrayList<PropertyValue>();
		ResultSet rs = null;
		try {
			String stmt = 
					"select i.id as iid, * from piece_info as i, property as p " +
					"where i.property = p.id and i.piece = " + piece.getId();
			rs = db.statement.executeQuery(stmt);
			while(rs.next()) {
				int id = rs.getInt("iid");
				int propertyId = rs.getInt("property");
				String name = rs.getString("name");
				String value = rs.getString("value");
				PropertyValue p = new PropertyValue(id, propertyId, name, value);
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new PiecePropertyContainer(piece, list);
		}
		
		return new PiecePropertyContainer(piece, list);
	}

	public List<Part> getParts(int id) {
		List<Part> list = new ArrayList<Part>();
		ResultSet rs = null;
		try {
			rs = db.statement.executeQuery("select * from part where piece=" + id);
			while(rs.next())
			{
				// read the result set
				Part part = new Part();
				part.setId(rs.getInt("id"));
				part.setPieceId(rs.getInt("piece"));
				part.setCreateDate(db.StringToDate(rs.getString("created")));
				list.add(part);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return list;
		}
		
		return list;
	}

}
