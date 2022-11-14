package com.shawtonabbey.pgem.database.db;

import com.shawtonabbey.pgem.database.DBC;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbDatabase {
	
	@Getter
	private String name;
	
	private DBC connection;

	public DBC getDbInstance() {
		return connection;
	}
	
	
	
}
