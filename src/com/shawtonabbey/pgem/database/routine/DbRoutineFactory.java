package com.shawtonabbey.pgem.database.routine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.BigIntValue;
import com.shawtonabbey.pgem.database.deserializers.Property;
import com.shawtonabbey.pgem.database.deserializers.TextValue;
import com.shawtonabbey.pgem.database.schema.DbSchema;

@Component
@Scope("singleton")
public class DbRoutineFactory {

	public List<DbRoutine> getRoutines(DBC connection, DbSchema dbSchema) throws IOException {

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

}
