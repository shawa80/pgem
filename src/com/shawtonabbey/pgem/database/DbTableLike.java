package com.shawtonabbey.pgem.database;

import java.util.List;

public interface DbTableLike {

	public String getName();
	public DBC getDbInstance();
	public List<DbColumn> getColumns();
	
}
