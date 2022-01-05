package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbConstraint {

	@Getter
	private String name;
	@Getter
	private DbTable table;
		
	@Getter
	private Long oid;
	
	public static List<DbConstraint> getConstraints(DBC connection, DbTable table) throws IOException {

		List<DbConstraint> results = new ArrayList<DbConstraint>();
		

		var sqlStr = "SELECT con.conname, con.oid" + 
				"       FROM pg_catalog.pg_constraint con" + 
				"            INNER JOIN pg_catalog.pg_class rel" + 
				"                       ON rel.oid = con.conrelid" + 
				"            INNER JOIN pg_catalog.pg_namespace nsp" + 
				"                       ON nsp.oid = connamespace" + 
				"       WHERE nsp.nspname = ?" + 
				"             AND rel.relname = ?;";

		var rs = connection.exec(sqlStr, ConstraintData.class, 
				table.getSchema().getName(), table.getName());

		for (var r : rs)
		{
			results.add(new DbConstraint(r.getConname(), table, r.getOid()));
		}
		
		return results;
	}
	
	public String getDefinition(DBC connection) throws IOException {
		
		var sqlStr = "select pg_get_constraintdef as text_value "
				+ "from pg_get_constraintdef(?, true);";

		var rs = connection.exec(sqlStr, TextValue.class, oid);
		
		return rs.stream().findFirst().get().getText_value();
	}
	
}

