package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbSequence implements Definable {

	@Getter
	private String name;
	
		
	public static List<DbSequence> getSequence(DBC connection, DbSchema dbSchema) throws IOException {

		List<DbSequence> results = new ArrayList<DbSequence>();
		
		ARecordSet rs;

		var sqlStr = "select sequence_name from information_schema.sequences " +
					"where sequence_schema = ? " +
					"order by sequence_name;";

		rs = connection.exec(sqlStr, dbSchema.getName());

		while (rs.next())
		{
			results.add(new DbSequence(rs.get("sequence_name")));
		}
		
		return results;
	}
	
	public String getDefinition(DBC connection) {
		
		return "";
	}



}
