package veno.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class SQLiteDatabase {
	Connection connection = null;
	Statement statement = null;
	
	public SQLiteDatabase() throws SQLException {
		// load the sqlite-JDBC driver using the current class loader
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			throw new SQLException("sqlite JDBC not available");
		}

		connection = DriverManager.getConnection("jdbc:sqlite:veno.db");
		statement = connection.createStatement();
		statement.setQueryTimeout(30);  // set timeout to 30 sec.
		statement.executeUpdate("PRAGMA foreign_keys = ON;");


	}
	
	void checkOrInsert(String table, String columns, String values) {
		ResultSet rs;
		try {
			String columnnames[] = columns.split(",");
			String columnvalues[] = values.split(",");
			if(columnnames.length != columnvalues.length || columnnames.length == 0) {
				return;
			}
			String whereclause = "";
			for(int i=0; i<columnnames.length; i++) {
				whereclause += columnnames[i] + "=" + columnvalues[i];
				if(i<columnnames.length-1) {
					whereclause += " and ";
				}
			}
			
			rs = statement.executeQuery("select " + columns + " from " + table + " where " + whereclause);
			if(rs.next())
			{
				return;
			}
			statement.executeUpdate("insert into " + table + "(" + columns + ") values (" + values + ")");
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void finalize() {
		try
		{
			if(connection != null)
				connection.close();
		}
		catch(SQLException e)
		{
			// connection close failed.
			System.err.println(e);
		}
	}
	
	public Date StringToDate(String str) {
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return formatter.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			GregorianCalendar gc = new GregorianCalendar(1970, 01, 01);
			return gc.getTime();
		}
	}
}
