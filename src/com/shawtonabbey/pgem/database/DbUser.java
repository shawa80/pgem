package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DbUser implements DbcProvider {

	@Getter
	private String name;
	
	private DBC connection;
			
	
	private DbUser (DBC connection, String name) throws IOException {
		
		this.name = name;
		this.connection = connection;
	}
	
	
	public static List<DbUser> getUsers(DbDatabase database) throws IOException {

		List<DbUser> results = new ArrayList<DbUser>();
		
		ARecordSet rs;

		String sqlStr = "SELECT usename " + 
				"FROM pg_catalog.pg_user";

		rs = database.getDbInstance().exec(sqlStr);

		while (rs.next())
		{
			results.add(new DbUser(database.getDbInstance(), rs.get("usename")));
		}
		
		return results;
	}


	@Override
	public DBC getDbInstance() {
		return connection;
	}	
	
	
}

