package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.database.deserializers.Property;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbConstraint {

	@Getter
	private String name;
		
	@Getter
	private Long oid;
	
	public static List<DbConstraint> getConstraints(DBC connection, DbTable table) throws IOException {

		var sqlStr = "SELECT con.conname, con.oid" + 
				"       FROM pg_catalog.pg_constraint con" + 
				"            INNER JOIN pg_catalog.pg_class rel" + 
				"                       ON rel.oid = con.conrelid" + 
				"            INNER JOIN pg_catalog.pg_namespace nsp" + 
				"                       ON nsp.oid = connamespace" + 
				"       WHERE nsp.nspname = ?" + 
				"             AND rel.relname = ?;";

		var c = new Constr<>(DbConstraint.class);
		var results = connection.execX(sqlStr, c, 
				table.getSchema().getName(), table.getName());
		
		return results;
	}
	
	public String getDefinition(DBC connection) throws IOException {
		
		var sqlStr = "select pg_get_constraintdef as text_value "
				+ "from pg_get_constraintdef(?, true);";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);
		
		return rs.stream().findFirst().orElseThrow().getText_value();
	}
	
}

