package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbRule {

	@Getter
	private String name;
	@Getter 
	private String def;
	
	public static List<DbRule> getRules(DBC connection, DbTableLike table) throws IOException {
			
		
		var sqlStr = "select rulename as name, definition as def "
				+ " from pg_rules"
				+ " where schemaname = ?"
				+ " and tablename = ?;";

		var c = new Constr<>(DbRule.class);
		var results = connection.execX(sqlStr, c,
				table.getSchema().getName(), table.getName());

		
		return results;
	}
}
