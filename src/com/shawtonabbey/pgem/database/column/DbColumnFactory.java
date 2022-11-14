package com.shawtonabbey.pgem.database.column;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.deserializers.Property;

@Component
@Scope("singleton")
public class DbColumnFactory {

	
	public List<DbColumn> getColumns(DBC connection, DbTableLike table) throws IOException {
		
		var sqlStr = "SELECT column_name, data_type, column_default, is_nullable, "
				+ " character_maximum_length " +
				"FROM information_schema.columns " + 
				"WHERE table_schema = ? " + 
				"  AND table_name   = ?";

		var c = new Property<>(Column.class);
		var r = connection.execX(sqlStr, c, 
				table.getSchema().getName(), table.getName());
		
		var cls = r.stream()
			.map((x) -> new DbColumn(table, x))
			.collect(Collectors.toList());
		
		return Collections.unmodifiableList(cls);
	}
	
}
