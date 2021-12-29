package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DbIndex {

	@Getter
	private String name;
	@Getter
	private String def;
	
	private DbIndex(String name, DbTable table, String def) {
		this.name = name;
		this.def = def;
	}
	


	public static List<DbIndex> getIndexes(DBC connection, DbTable table) throws IOException {
		
		var results = new ArrayList<DbIndex>();
		
		ARecordSet rs;

		var sqlStr = "select indexname, indexdef " + 
		"from pg_indexes " +
		" where schemaname = ? " +
		" and tablename = ?";

		rs = connection.exec(sqlStr, table.getSchema().getName(), table.getName());

		if (rs != null) {
			
			while (rs.next())
			{
					results.add(new DbIndex(rs.get("indexname"), table, 
							rs.get("indexdef")));
			}
			rs.close();
		}
		
		return results;
	}



}
