package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbIndex {

	@Getter
	private String name;
	@Getter
	private String def;
	

	public static List<DbIndex> getIndexes(DBC connection, DbTable table) throws IOException {
		
		var sqlStr = "select indexname, indexdef " + 
		"from pg_indexes " +
		" where schemaname = ? " +
		" and tablename = ?";

		var c = new Constr<>(DbIndex.class);
		var results = connection.execX(sqlStr, c, 
				table.getSchema().getName(), table.getName());
		
		return results;
	}



}
