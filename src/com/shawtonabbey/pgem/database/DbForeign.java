package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbForeign {

	@Getter
	private String name;
	
	public static List<DbForeign> getForeign(DBC connection, DbTable table) throws IOException {
		
		var sqlStr = "SELECT " +
				"       conname AS foreign_key " + 
				"FROM   pg_constraint " + 
				"WHERE  contype = 'f' " + 
				"AND    connamespace = 'public'::regnamespace   " + 
				"ORDER  BY conrelid::regclass::text, contype DESC;";

		var c = new Constr<>(DbForeign.class);
		var results = connection.execX(sqlStr, c);
		
		return results;
	}
	
	
}
