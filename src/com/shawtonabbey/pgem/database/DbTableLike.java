package com.shawtonabbey.pgem.database;

import java.util.List;

public interface DbTableLike {

	public String getName();
	public List<DbColumn> getColumns();
	
}
