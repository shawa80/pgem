package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbServer {

	@Getter
	private String name;
	
	
	public static List<DbDatabase> getDatabases(DBC connection, DbServer server) throws IOException {
	
		List<DbDatabase> results = new ArrayList<DbDatabase>();
		
		ARecordSet rs;

		var sqlStr = "SELECT datname FROM pg_database " +
		"WHERE datistemplate = false;";

		rs = connection.exec(sqlStr);

		while (rs.next())
		{
			var dbName = rs.get("datname");
			
			try {
				results.add(new DbDatabase(dbName, server, connection.connect(dbName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return results;
	
	}
		
}
