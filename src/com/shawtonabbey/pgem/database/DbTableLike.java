package com.shawtonabbey.pgem.database;

import com.shawtonabbey.pgem.database.schema.DbSchema;

public interface DbTableLike {

	public DbSchema getSchema();
	public String getName();
	
}
