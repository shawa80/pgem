package com.shawtonabbey.pgem.database;

import java.text.ParseException;

public enum DbType {

	SmallInt(Byte.class, "smallint"),
	Varchar(String.class, "character varying"),
	NVarchar(String.class, "varchar");
	
	Class<?> javaType;
	String name;
	
	DbType(Class<?> javaType, String name) {
		this.javaType = javaType;
		this.name = name;
	}
	
	public static DbType to(String name) throws ParseException {
		for (var p : DbType.values()) {

				if (p.name.equals(name))
					return p;
		}
		throw new ParseException("Enum not found", 0);
	}
}
