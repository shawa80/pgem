package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbSequence implements Definable, DbcProvider {

	@Getter
	private String name;
	
	private DBC connection;
		
	public static List<DbSequence> getSequence(DbSchema dbSchema) throws IOException {

		List<DbSequence> results = new ArrayList<DbSequence>();
		
		ARecordSet rs;

		var sqlStr = "select sequence_name from information_schema.sequences " +
					"where sequence_schema = ? " +
					"order by sequence_name;";

		rs = dbSchema.getDbInstance().exec(sqlStr, dbSchema.getName());

		while (rs.next())
		{
			results.add(new DbSequence(rs.get("sequence_name"), dbSchema.getDbInstance()));
		}
		
		return results;
	}
	
	public String getDefinition() {
		
		return "";
	}

	@Override
	public DBC getDbInstance() {
		return connection;
	}


}
