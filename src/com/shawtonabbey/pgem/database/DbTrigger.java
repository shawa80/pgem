package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbTrigger {

	@Getter
	private String name;
	@Getter
	private Long oid;
	
		
	public static List<DbTrigger> getTriggers(DBC connection, DbTable table) throws IOException {

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
	
	public String getDef(DBC connection) throws IOException {

		var sqlStr = "select pg_get_triggerdef as text_value "
				+ "from pg_get_triggerdef(?, true);";

		var rs = connection.exec(sqlStr, TextValue.class, oid);
		
		var def = rs.stream()
			.findFirst()
			.get().getText_value();
		
		return def;
	}
	

}

