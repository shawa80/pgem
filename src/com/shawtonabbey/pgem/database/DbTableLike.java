package com.shawtonabbey.pgem.database;

import java.util.List;

public interface DbTableLike {

	public DbSchema getSchema();
	public String getName();
	public List<DbColumn> getColumns();
	
}
