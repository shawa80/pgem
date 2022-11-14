package com.shawtonabbey.pgem.plugin.ddl.index;

import java.util.List;

import com.shawtonabbey.pgem.database.column.DbColumn;

public interface IndexModel {

	public String getTableName();
	public void setTableName(String name);
	
	public String getIndexName();
	public void setIndexName(String name);
	
	public boolean isUnique();
	public void setUnique(boolean unique);
	
	public boolean isConcurrently();
	public void setConcurrently(boolean concurrently);
	
	public void addColumn(DbColumn c);
	public void addAll(List<DbColumn> c);
	public void removeColumn(DbColumn c);
	public List<DbColumn> getColumns();
	
	public void clearColumns();
}
