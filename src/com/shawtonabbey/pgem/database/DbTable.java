package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
		
		var sqlStr = "SELECT column_name, data_type " + 
				"FROM information_schema.columns " + 
				"WHERE table_schema = ? " + 
				"  AND table_name   = ?";

		var r = connection.exec(sqlStr, Column.class, schema.getName(), name);
		
		var cls = r.stream()
			.map((x) -> new DbColumn(connection, this, x))
			.collect(Collectors.toList());
		
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
