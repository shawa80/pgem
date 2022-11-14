package com.shawtonabbey.pgem.database.constraint;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.deserializers.Constr;


@Component
@Scope("singleton")
public class DbConstraintFactory {

	public List<DbConstraint> getConstraints(DBC connection, DbTable table) throws IOException {

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
	
}
