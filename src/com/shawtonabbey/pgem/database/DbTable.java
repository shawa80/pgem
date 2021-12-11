package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Getter;

public class DbTable implements DbcProvider, DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	
	@Getter
	private List<DbColumn> columns;
	
	private DBC connection;
	
	private DbTable(DBC connection, String name, DbSchema schema) throws IOException {
		this.name = name;
		this.schema = schema;
		this.connection = connection;
		
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
			cls.add(new DbColumn(connection, this, n, t));
		}
		rs.close();

		columns = Collections.unmodifiableList(cls);
	
	}
	
	public static List<DbTable> getTables(DbSchema schema) throws IOException {
		
		List<DbTable> results = new ArrayList<DbTable>();

		ARecordSet rs;
		
		var sqlStr = "select table_name from information_schema.tables " +
		"WHERE table_schema=? AND table_type='BASE TABLE' " +
		"order by table_name;";

		rs = schema.getDbInstance().exec(sqlStr, schema.getName());


		while (rs.next())
		{
			results.add(new DbTable(schema.getDbInstance(), rs.get("table_name"), schema));
		}
		rs.close();
		
		return results;
	}

	@Override
	public DBC getDbInstance() {
		return connection;
	}


}
