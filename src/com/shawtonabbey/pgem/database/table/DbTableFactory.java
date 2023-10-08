package com.shawtonabbey.pgem.database.table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.ARecordSet;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.BigIntValue;
import com.shawtonabbey.pgem.database.deserializers.Property;
import com.shawtonabbey.pgem.database.schema.DbSchema;

@Component
@Scope("singleton")
public class DbTableFactory {

	@Autowired
	protected ApplicationContext appContext;
	
	public List<DbTable> getTables(DBC connection, DbSchema schema) throws IOException {
		
		List<DbTable> results = new ArrayList<DbTable>();

		ARecordSet rs;
		
		var sqlStr = "select table_name from information_schema.tables " +
		"WHERE table_schema=? AND table_type='BASE TABLE' " +
		"order by table_name;";

		rs = connection.exec(sqlStr, schema.getName());


		while (rs.next())
		{
			var tableName = rs.get("table_name");
			
			var c = new Property<>(BigIntValue.class);
			var oid = connection.execX("SELECT ?::regclass::oid as value", c, 
					schema.getName() + "." + tableName).stream().findFirst().get();
			results.add(new DbTable(tableName, schema, oid.getValue()));
			
		}
		rs.close();
		
		return results;
	}
	
}
