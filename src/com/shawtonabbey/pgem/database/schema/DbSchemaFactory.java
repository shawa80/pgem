package com.shawtonabbey.pgem.database.schema;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.db.DbDatabase;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbSchemaFactory {

	public List<DbSchema> getSchemas(DBC connection, boolean loadSysSchema) throws IOException {
		

		var sqlStr = "select schema_name "+
		"from information_schema.schemata schema_name " +
		(!loadSysSchema ? "where schema_name not like 'pg%' " : "") +
		"order by schema_name;";

		var c = new Constr<>(DbSchema.class);
		var rs = connection.execX(sqlStr, c);

		var results = rs.stream()
			.filter(s-> !"information_schema".equals(s.getName()))
			.collect(Collectors.toList());
			
		
		return results;
	}

	
}
