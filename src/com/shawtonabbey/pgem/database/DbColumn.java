package com.shawtonabbey.pgem.database;

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


	@Override
	public String toString() {
		return name;
	}
}
