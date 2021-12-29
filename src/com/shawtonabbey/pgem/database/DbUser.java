package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DbUser {

	@Getter
	private String name;
	

	private DbUser (DBC connection, String name) throws IOException {
		
		this.name = name;
	}
	
	
	public static List<DbUser> getUsers(DBC connection, DbDatabase database) throws IOException {

		List<DbUser> results = new ArrayList<DbUser>();
		
		ARecordSet rs;

		String sqlStr = "SELECT usename " + 
				"FROM pg_catalog.pg_user";

		rs = connection.exec(sqlStr);

		while (rs.next())
		{
			results.add(new DbUser(connection, rs.get("usename")));
		}
		
		return results;
	}	
	
	
}

