package com.shawtonabbey.pgem.database.foreign;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.database.table.DbTable;

@Component
@Scope("singleton")
public class DbForeignFactory {

	public List<DbForeign> getForeign(DBC connection, DbTable table) throws IOException {
		
//		var sqlStr = "SELECT " +
//				"       conname AS foreign_key " + 
//				"FROM   pg_constraint " + 
//				"WHERE  contype = 'f' " + 
//				"AND    connamespace = 'public'::regnamespace   " + 
//				"ORDER  BY conrelid::regclass::text, contype DESC;";

		var sqlStr = "SELECT " + 
			"    tc.constraint_name as name, " + 
			"    tc.table_schema as primary_schema, " + 
			"    tc.table_name as primary_table, " + 
			"    kcu.column_name as primary_column, " + 
			"    ccu.table_schema AS foreign_table_schema," + 
			"    ccu.table_name AS foreign_table_name," + 
			"    ccu.column_name AS foreign_column_name " + 
			"FROM " + 
			"    information_schema.table_constraints AS tc " + 
			"    JOIN information_schema.key_column_usage AS kcu ON tc.constraint_name = kcu.constraint_name AND tc.table_schema = kcu.table_schema " + 
			"    JOIN information_schema.constraint_column_usage AS ccu ON ccu.constraint_name = tc.constraint_name AND ccu.table_schema = tc.table_schema " + 
			"WHERE tc.constraint_type = 'FOREIGN KEY' " + 
			"AND ccu.table_schema = ? " +
			"AND ccu.table_name = ?;";
		
		
		var c = new Constr<>(DbForeign.class);
		var results = connection.execX(sqlStr, c, table.getSchema().getName(), table.getName());
		
		return results;
	}
	
}
