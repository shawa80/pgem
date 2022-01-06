package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.crypto.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class DbTrigger {

	@Getter
	private String name;
	
	@Getter
	private Long oid;
	
		
	public static List<DbTrigger> getTriggers(DBC connection, DbTable table) throws IOException {

		var sqlStr = "select t.oid as oid, t.tgname as name from pg_trigger t " + 
				"join pg_class c on t.tgrelid = c.oid " + 
				"join pg_namespace n on c.relnamespace = n.oid " + 
				"where c.relname = ? " + 
				"and n.nspname = ?";

		var rs = connection.exec(sqlStr, OidName.class, table.getName(),table.getSchema().getName());

		var results = rs.stream()
			.map(v-> new DbTrigger(v.getName(), v.getOid()))
			.collect(Collectors.toList());
		
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

