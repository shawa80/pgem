package com.shawtonabbey.pgem.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbDatabase implements DbcProvider {
	
	@Getter
	private String name;
	@Getter
	private DbServer server; 
	
	private DBC connection;

	@Override
	public DBC getDbInstance() {
		return connection;
	}
	
}
