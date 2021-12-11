package com.shawtonabbey.pgem.query;

import com.shawtonabbey.pgem.Savable;
import com.shawtonabbey.pgem.database.DBC;


public interface QueryWindow extends Savable {

	void setConnection(DBC c);

	void setSql(String sql);

	String getSql();
	void enableSql();
	void init();
	
	
}