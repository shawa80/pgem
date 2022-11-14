package com.shawtonabbey.pgem.database;

import java.util.List;

import com.shawtonabbey.pgem.database.column.DbColumn;

public interface DbColumnCollection {

	public List<DbColumn> getColumns();
}
