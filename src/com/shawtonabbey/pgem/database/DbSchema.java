package com.shawtonabbey.pgem.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class DbSchema implements DbcProvider {

	@Getter
	private String name;
	@Getter
	private DbDatabase database;
	
	private DBC connection;
	
	public static List<DbSchema> getSchemas(DbDatabase database) throws IOException {
		return getSchemas(database, false);
	}
	
	public static List<DbSchema> getSchemas(DbDatabase database, boolean loadSysSchema) throws IOException {
		
		List<DbSchema> results = new ArrayList<DbSchema>();
		
		ARecordSet rs;
		var sqlStr = "select schema_name "+
		"from information_schema.schemata schema_name " +
		(!loadSysSchema ? "where schema_name not like 'pg%' " : "") +
		"order by schema_name;";

		rs = database.getDbInstance().exec(sqlStr);

		if (rs != null) {
			
			while (rs.next())
			{
				if (!"information_schema".equals(rs.get("schema_name")))
					results.add(new DbSchema(rs.get("schema_name"), database, database.getDbInstance()));
			}
			rs.close();
		}
		
		return results;
	}

	@Override
	public DBC getDbInstance() {
		return connection;
	}

	
}
