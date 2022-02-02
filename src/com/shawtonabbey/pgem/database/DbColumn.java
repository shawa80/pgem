package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.shawtonabbey.pgem.database.deserializers.Property;

import lombok.Getter;

public class DbColumn {
	
	@Getter
	private String name;

	@Getter
	private String type;
	
	@Getter
	private DbTableLike parent;
	
	@Getter
	private boolean isNullable;
	
	@Getter
	private String defaultValue;
		
	@Getter
	private Integer characterMaximumLength;
	
	public DbColumn(DbTableLike parent, Column c) {
				
		this(parent, c.getColumn_name(), 
				c.getData_type(), 
				"YES".equals(c.getIs_nullable()), 
				c.getColumn_default(),
				c.getCharacter_maximum_length());
	}
	
	public DbColumn(DbTableLike parent, String name, String type, boolean isNullable, String defaultValue,
			Integer characterMaximumLength) {
		
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.isNullable = isNullable;
		this.defaultValue = defaultValue;
		this.characterMaximumLength = characterMaximumLength;
	}

	public static List<DbColumn> getColumns(DBC connection, DbTableLike table) throws IOException {
		
		var sqlStr = "SELECT column_name, data_type, column_default, is_nullable, "
				+ " character_maximum_length " +
				"FROM information_schema.columns " + 
				"WHERE table_schema = ? " + 
				"  AND table_name   = ?";

		var c = new Property<>(Column.class);
		var r = connection.execX(sqlStr, c, 
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
