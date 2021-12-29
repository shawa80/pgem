package com.shawtonabbey.pgem.database;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.shawtonabbey.pgem.database.ui.HeaderCollection;
import com.shawtonabbey.pgem.database.ui.HeaderReady;
import com.shawtonabbey.pgem.database.ui.Row;
import com.shawtonabbey.pgem.database.ui.RowReady;
import com.shawtonabbey.pgem.database.ui.Status;
import com.shawtonabbey.pgem.database.ui.StatusReady;
import com.shawtonabbey.pgem.swingUtils.SwingInvoker;

import lombok.Getter;


public class DBC implements AutoCloseable
{
	private Connection db;
	
	
	private String address;
	private String port;
	@Getter
	private String database;
	private String user = null;
	private String pass = null;

	private PreparedStatement stm;
	private boolean requestStop;
	
	private DBC(String a, String po, String d) {
		address = a;
		port = po;
		database = d;
	}
	
	private DBC(String a, String po, String d, String u, String pa)
	{
		address = a;
		port = po;
		database = d;
		user = u;
		pass = pa;
		
	}

	
	public DBC connect(String dbName) throws IOException{
	
		DBC conn;
		if (user == null)
			conn = connect(address, port, dbName);
		else
			conn = connect(address, port, dbName, user, pass);
	
		return conn;
	}

	public Connection getRaw() {
		return db;
	}
	
	
	public DBC duplicate() throws IOException {
		
		var conn = connect(address, port, database, user, pass);
		
		return conn;
	}
	
	
	public static DBC connect(String address, String po, String database) throws IOException
	{
		var conn = new DBC(address, po, database);
		
		try {
			
			Properties props = new Properties();			
			conn.db = DriverManager.getConnection("jdbc:postgresql://" 
					+ address + "/" + database, props);
		}
		catch (SQLException e)
		{
			throw new IOException(e.getMessage(), e);
		}

		return conn;
	}
	
	public static DBC connect(String address, String po, String database, String user, String pass) throws IOException
	{
		var conn = new DBC(address, po, database, user, pass);
		
		try {
			
			conn.db = DriverManager.getConnection("jdbc:postgresql://" 
					+ address + "/" + database, user, pass);
			
		}
		catch (SQLException e)
		{
			throw new IOException(e.getMessage(), e);
		}

		return conn;
	}
	public void disconnect()
	{
		try {
			db.close();
		}
		catch (SQLException e)
		{
			System.out.println("Error disconnecting from db");
		}
	}


		
	public void runQuery(HeaderReady header, RowReady row, StatusReady status, 
			String sqlstr, Object... args) {
		
		
		SwingInvoker.mustBeOnWorkThread();
		
		try (PreparedStatement stm = db.prepareStatement(sqlstr)) {			

			this.stm = stm;
			requestStop = false;
			for (var i = 0; i < args.length; i++) {
				stm.setObject(i+1, args[i]);
			}
					
			if (stm.execute())
			{
				var resultSet = stm.getResultSet();
				var columnInfo = resultSet.getMetaData();
				
				header.ready(new HeaderCollection(columnInfo));
				
				while (resultSet.next() && requestStop == false) {
					row.ready(new Row(resultSet));
				}

				var w = stm.getWarnings();
				var wrn = w == null ? "" : w.getMessage();
				status.ready(new Status(wrn));
			}
			else
			{
				var count = stm.getUpdateCount();
				var w = stm.getWarnings();
				var wrn = w == null ? "" : w.getMessage();
				status.ready(new Status(wrn, count));
			}
			
		} catch (Exception e)
		{
			status.ready(new Status(e.getMessage()));
			System.out.println(e.getMessage());
		} finally {
			this.stm = null;
		}
	}
	
	public void stop() {
		if (this.stm != null) {
			try {
				requestStop = true;
				this.stm.cancel();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public <T> List<T> exec(String sqlstr, Class<T> cls, Object... args) throws IOException
	{		
		try {			

			var stm = db.prepareStatement(sqlstr);
			
			for (int i = 0; i < args.length; i++) {
				stm.setObject(i+1, args[i]);
			}
			
			try (var recordSet = new ARecordSet(stm)) {
				recordSet.execute();

				var results = new ArrayList<T>();
				var cols = recordSet.columnInfo.getColumnCount();
				
				while (recordSet.next()) {
		
					var c = cls.newInstance();
					for (var i = 1; i <= cols; i++) {
						var colName = recordSet.getColumnName(i);
						var className = recordSet.columnInfo.getColumnClassName(i);
						var value = recordSet.getValue(i);
						try {
							var method = cls.getMethod("set" + fix(colName), Class.forName(className));
							method.invoke(c, value);
						} catch (NoSuchMethodException e) {}
					}
					results.add(c);
				}
				return results;
			}

		} catch (Exception e)
		{
			System.out.println("error on createStatement();");
			throw new IOException(e.getMessage(), e);
		}

	}
	private String fix(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	
	public ARecordSet exec(String sqlstr, Object... args) throws IOException
	{		
		ARecordSet recordSet = null;
		try {			

			var stm = db.prepareStatement(sqlstr);
			
			for (int i = 0; i < args.length; i++) {
				stm.setObject(i+1, args[i]);
			}
			
			recordSet = new ARecordSet(stm);
			recordSet.execute();
		} catch (Exception e)
		{
			System.out.println("error on createStatement();");
			throw new IOException(e.getMessage(), e);
		}
		return recordSet;
	}

	@Override
	public void close() throws Exception {
		
		db.close();
		
	}

}
