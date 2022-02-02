package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Property;

import lombok.Getter;

public class DbTable implements DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	@Getter
	private List<DbColumn> columns;
	
	@Getter
	private long oid;
	
	private DbTable(DBC connection, String name, DbSchema schema, Long oid) throws IOException {
		this.name = name;
		this.schema = schema;
		
		this.columns = DbColumn.getColumns(connection, this);
		this.oid = oid;
	}
	
	public static List<DbTable> getTables(DBC connection, DbSchema schema) throws IOException {
		
		List<DbTable> results = new ArrayList<DbTable>();

		ARecordSet rs;
		
		var sqlStr = "select table_name from information_schema.tables " +
		"WHERE table_schema=? AND table_type='BASE TABLE' " +
		"order by table_name;";

		rs = connection.exec(sqlStr, schema.getName());


		while (rs.next())
		{
			var tableName = rs.get("table_name");
			
			var c = new Property<>(BigIntValue.class);
			var oid = connection.execX("SELECT ?::regclass::oid as value", c, 
					schema.getName() + "." + tableName).stream().findFirst().get();
			results.add(new DbTable(connection, tableName, schema, oid.getValue()));
		}
		rs.close();
		
		return results;
	}





}
