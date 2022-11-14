package com.shawtonabbey.pgem.database.trigger;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.database.table.DbTable;

@Component
@Scope("singleton")
public class DbTriggerFactory {

	public List<DbTrigger> getTriggers(DBC connection, DbTable table) throws IOException {

		var sqlStr = "select t.tgname as name, t.oid as oid " +
				"from pg_trigger t " + 
				"join pg_class c on t.tgrelid = c.oid " + 
				"join pg_namespace n on c.relnamespace = n.oid " + 
				"where c.relname = ? " + 
				"and n.nspname = ?";

		
		var c = new Constr<>(DbTrigger.class);
		var results = connection.execX(sqlStr, c, 
				table.getName(), table.getSchema().getName());
		
		return results;
	}
	
}
