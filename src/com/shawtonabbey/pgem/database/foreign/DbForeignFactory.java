package com.shawtonabbey.pgem.database.foreign;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbForeignFactory {

	public List<DbForeign> getForeign(DBC connection, DbTable table) throws IOException {
		
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
