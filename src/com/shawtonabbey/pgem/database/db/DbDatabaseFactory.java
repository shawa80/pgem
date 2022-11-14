package com.shawtonabbey.pgem.database.db;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.ARecordSet;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.server.DbServer;

@Component
@Scope("singleton")
public class DbDatabaseFactory {

	public List<DbDatabase> getDatabases(DBC connection, DbServer server) throws IOException {
		
		List<DbDatabase> results = new ArrayList<DbDatabase>();
		
		ARecordSet rs;

		var sqlStr = "SELECT datname FROM pg_database " +
		"WHERE datistemplate = false;";

		rs = connection.exec(sqlStr);

		while (rs.next())
		{
			var dbName = rs.get("datname");
			
			try {
				results.add(new DbDatabase(dbName, connection.connect(dbName)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return results;
	
	}
	
}
