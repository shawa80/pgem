package com.shawtonabbey.pgem.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shawtonabbey.pgem.database.DBC;

@AllArgsConstructor
public class DbRoutine implements Definable {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;
		
	
	public static List<DbRoutine> getRoutines(DBC connection, DbSchema dbSchema) throws IOException {

		List<DbRoutine> results = new ArrayList<DbRoutine>();
		
		ARecordSet rs;

		var sqlStr = "select routine_name, * from information_schema.routines " +
		"where routine_schema = ? " +
		"order by routine_name;";

		rs = connection.exec(sqlStr, dbSchema.getName());

		while (rs.next())
		{
			results.add(new DbRoutine(rs.get("routine_name"), dbSchema));
		}
		
		return results;
	}

	
	public String getDefinition(DBC connection) throws IOException {
		
		var result = "create view " + schema.getName()  + "." + name + "\nas\n" ;
		
		var sql = "select view_definition from information_schema.views where table_name = ?;";
				
		var def = connection.exec(sql, name);
		
		while (def.next()) {
			result += def.get(1);
		}
	
		
		return result;
	}
}

