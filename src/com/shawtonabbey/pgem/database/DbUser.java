package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbUser {

	@Getter
	private String name;
	
	public static List<DbUser> getUsers(DBC connection, DbDatabase database) throws IOException {

		String sqlStr = "SELECT usename " + 
				"FROM pg_catalog.pg_user";

		var c = new Constr<>(DbUser.class);
		var results = connection.execX(sqlStr, c);

		return results;
	}	
	
	
}

