package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbSequence {

	@Getter
	private String name;
		
	public static List<DbSequence> getSequence(DBC connection, DbSchema dbSchema) throws IOException {

		var sqlStr = "select sequence_name " +
				    "from information_schema.sequences " +
					"where sequence_schema = ? " +
					"order by sequence_name;";

		var results = connection.execCon(sqlStr, DbSequence.class, dbSchema.getName());
		
		return results;
	}
	
}
