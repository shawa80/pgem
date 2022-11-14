package com.shawtonabbey.pgem.database.index;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbIndexFactory {

	public List<DbIndex> getIndexes(DBC connection, DbTable table) throws IOException {
		
		var sqlStr = "select indexname, indexdef " + 
		"from pg_indexes " +
		" where schemaname = ? " +
		" and tablename = ?";

		var c = new Constr<>(DbIndex.class);
		var results = connection.execX(sqlStr, c, 
				table.getSchema().getName(), table.getName());
		
		return results;
	}
	
}
