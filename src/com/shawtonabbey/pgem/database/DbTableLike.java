package com.shawtonabbey.pgem.database;

import java.util.List;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.schema.DbSchema;

public interface DbTableLike {

	public DbSchema getSchema();
	public String getName();
	public List<DbColumn> getColumns();
	
}
