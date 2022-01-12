package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbUser {

	@Getter
	private String name;
	
	public static List<DbUser> getUsers(DBC connection, DbDatabase database) throws IOException {

		String sqlStr = "SELECT usename " + 
				"FROM pg_catalog.pg_user";

		var results = connection.execCon(sqlStr, DbUser.class);

		return results;
	}	
	
	
}

