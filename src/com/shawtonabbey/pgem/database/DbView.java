package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class DbView implements Definable, DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;
		
	@Getter
	private List<DbColumn> columns;
	
	
	private DbView (DBC connection, String name, DbSchema schema) throws IOException {
		
		this.name = name;
		this.schema = schema;
		
		ARecordSet rs;
		var sqlStr = "SELECT column_name, data_type " + 
				"FROM information_schema.columns " + 
				"WHERE table_schema = ? " + 
				"  AND table_name   = ?";
		rs = connection.exec(sqlStr, schema.getName(), name);
		
		List<DbColumn> cls = new ArrayList<>();
		while (rs.next()) {
			var n = rs.get("column_name");
			var t = rs.get("data_type");
			cls.add(new DbColumn(this, n, t));
		}
		rs.close();

		columns = Collections.unmodifiableList(cls);
		
	}
	
	public String getDefinition(DBC connection) throws IOException {
		
		var result = "create view " + schema.getName()  + "." + name + "\nas\n" ;
		
		var sql = "select view_definition from information_schema.views where table_name = ?;";
				
		var def = connection.exec(sql, name);
		
		while (def.next()) {
			result += def.get(1);
		}
	
		
		return result;
	}

	
	public static List<DbView> getViews(DBC connection, DbSchema schema) throws IOException {

		List<DbView> results = new ArrayList<DbView>();
		
		ARecordSet rs;

		String sqlStr = "select table_name from information_schema.tables " +
		"WHERE table_schema=? AND table_type='VIEW' " +
		"order by table_name;";

		rs = connection.exec(sqlStr, schema.getName());

		while (rs.next())
		{
			results.add(new DbView(connection, rs.get("table_name"), schema));
		}
		
		return results;
	}
	
	
}
