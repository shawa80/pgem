package com.shawtonabbey.pgem.database;

import lombok.Getter;

public class DbColumn implements DbcProvider {
	
	@Getter
	private String name;

	@Getter
	private String type;
	
	@Getter
	private DbTableLike parent;
	
	private DBC connection;
	
	public DbColumn(DBC connection, DbTableLike parent, String name, String type) {
		
		this.connection = connection;
		this.name = name;
		this.type = type;
		this.parent = parent;
	}
	
	@Override
	public DBC getDbInstance() {
		return connection;
	}

	@Override
	public String toString() {
		return name;
	}
}
