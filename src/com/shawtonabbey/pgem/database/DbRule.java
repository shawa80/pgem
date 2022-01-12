package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import lombok.Getter;

public class DbRule {

	@Getter
	private String name;
	@Getter 
	private String def;
	
	public DbRule(String name, String def) {
		this.name = name;
		this.def = def;
	}
	
	public static List<DbRule> getRules(DBC connection, DbTableLike table) throws IOException {
		
		var sqlStr = "select rulename as name, definition as def "
				+ " from pg_rules"
				+ " where schemaname = ?"
				+ " and tablename = ?;";

		var results = connection.execCon(sqlStr, DbRule.class,
				table.getSchema().getName(), table.getName());

		
		return results;
	}
}
