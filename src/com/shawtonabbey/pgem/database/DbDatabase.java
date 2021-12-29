package com.shawtonabbey.pgem.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbDatabase {
	
	@Getter
	private String name;
	@Getter
	private DbServer server; 
	
	private DBC connection;

	public DBC getDbInstance() {
		return connection;
	}
	
}
