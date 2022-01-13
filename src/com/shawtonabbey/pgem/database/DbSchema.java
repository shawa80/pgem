package com.shawtonabbey.pgem.database;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DbSchema {

	@Getter
	private String name;
	
	public static List<DbSchema> getSchemas(DBC connection, DbDatabase database, boolean loadSysSchema) throws IOException {
		

		var sqlStr = "select schema_name "+
		"from information_schema.schemata schema_name " +
		(!loadSysSchema ? "where schema_name not like 'pg%' " : "") +
		"order by schema_name;";

		var rs = connection.execCon(sqlStr, DbSchema.class);

		var results = rs.stream()
			.filter(s-> !"information_schema".equals(s.name))
			.collect(Collectors.toList());
			
		
		return results;
	}


}
