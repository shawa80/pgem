package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class DbColumn {
	
	@Getter
	private String name;

	@Getter
	private String type;
	
	@Getter
	private DbTableLike parent;
		
	public DbColumn(DbTableLike parent, Column c) {
		this(parent, c.getColumn_name(), c.getData_type());
	}
	
	public DbColumn(DbTableLike parent, String name, String type) {
		
		this.name = name;
		this.type = type;
		this.parent = parent;
	}

	public static List<DbColumn> getColumns(DBC connection, DbTableLike table) throws IOException {
		
		var sqlStr = "SELECT column_name, data_type " + 
				"FROM information_schema.columns " + 
				"WHERE table_schema = ? " + 
				"  AND table_name   = ?";

		var r = connection.exec(sqlStr, Column.class, 
				table.getSchema().getName(), table.getName());
		
		var cls = r.stream()
			.map((x) -> new DbColumn(table, x))
			.collect(Collectors.toList());
		
		return Collections.unmodifiableList(cls);
	}
	
	
	@Override
	public String toString() {
		return name;
	}
}
