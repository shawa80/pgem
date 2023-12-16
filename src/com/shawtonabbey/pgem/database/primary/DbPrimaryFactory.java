package com.shawtonabbey.pgem.database.primary;

import java.io.IOException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.database.table.DbTable;


@Component
@Scope("singleton")
public class DbPrimaryFactory {

	public DbPrimaryKey getPrimary(DBC connection, DbTable table) throws IOException {
		
		var sqlStr = "SELECT               \n" + 
				"  pg_attribute.attname as colname \n" + 
				//"  format_type(pg_attribute.atttypid, pg_attribute.atttypmod) \n" + 
				"FROM pg_index, pg_class, pg_attribute, pg_namespace \n" + 
				"WHERE \n" + 
				"  pg_class.oid = ?::regclass AND \n" + 
				"  indrelid = pg_class.oid AND \n" + 
				"  nspname = ? AND \n" + 
				"  pg_class.relnamespace = pg_namespace.oid AND \n" + 
				"  pg_attribute.attrelid = pg_class.oid AND \n" + 
				"  pg_attribute.attnum = any(pg_index.indkey)\n" + 
				" AND indisprimary\n";

		var c = new Constr<>(DbPrimary.class);
		var results = connection.execX(sqlStr, c, 
				table.getName(), table.getSchema().getName());
		
		var key = new DbPrimaryKey();
		results.stream().forEach(key::add);
		
		return key;
	}
	
}