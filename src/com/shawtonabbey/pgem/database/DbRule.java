package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DbRule {

	@Getter
	private String name;
	@Getter
	private String def;
	
	private DbRule(String name, DbTableLike table, String def) {
		this.name = name;
		this.def = def;
	}
	


	public static List<DbRule> getRules(DBC connection, DbTableLike table) throws IOException {
		
		var results = new ArrayList<DbRule>();
		
		ARecordSet rs;

		var sqlStr = "select rulename, definition from pg_rules"
				+ " where schemaname = ?"
				+ " and tablename = ?;";

		rs = connection.exec(sqlStr, table.getSchema().getName(), table.getName());

		if (rs != null) {
			
			while (rs.next())
			{
					results.add(new DbRule(rs.get("rulename"), table, 
							rs.get("definition")));
			}
			rs.close();
		}
		
		return results;
	}
}
