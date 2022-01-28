package com.shawtonabbey.pgem.database;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Property;

@AllArgsConstructor
public class DbRoutine {

	@Getter
	private String name;
		
	@Getter
	private long oid;
	
	public static List<DbRoutine> getRoutines(DBC connection, DbSchema dbSchema) throws IOException {

		List<DbRoutine> results = new ArrayList<DbRoutine>();
		

		var sqlStr = "select routine_name as text_value from information_schema.routines " +
		"where routine_schema = ? " +
		"order by routine_name;";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, dbSchema.getName());

		var b = new Property<>(BigIntValue.class);
		for (var name : rs)
		{
			var proc = name.getText_value();
			var schema = dbSchema.getName();
			
			
			var oids = connection.execX("select p.oid as value from pg_proc p " + 
					"join pg_namespace ns on p.pronamespace = ns.oid " + 
					"where proname = ? " + 
					"and ns.nspname = ?",
					b, proc, schema);
			
			var oid = oids.stream().findFirst().get().getValue();
			
			results.add(new DbRoutine(proc, oid));
		}
		
		return results;
	}

	
	public String getReturnType(DBC connection) throws IOException {

		var sqlStr = "select pg_get_function_result as text_value "
				+ "from pg_get_function_result(?)";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);

		return rs.stream()
			.findFirst()
			.get().getText_value();
		
	}

	
	public List<String> getRoutinesParams(DBC connection) throws IOException {

		var sqlStr = "select pg_get_function_arguments as text_value "
				+ "from pg_get_function_arguments(?);";

		var c= new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);

		
		var params = rs.stream()
			.findFirst()
			.get().getText_value()
			.split(",");
		
		return Stream.of(params)
			.map (elem -> elem.trim())
			.collect(Collectors.toList());
		
	}
	
}

