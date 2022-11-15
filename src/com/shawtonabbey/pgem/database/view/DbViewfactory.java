package com.shawtonabbey.pgem.database.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.ARecordSet;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.schema.DbSchema;

@Component
@Scope("singleton")
public class DbViewfactory {

	@Autowired
	protected ApplicationContext appContext;
	
	public List<DbView> getViews(DBC connection, DbSchema schema) throws IOException {

		List<DbView> results = new ArrayList<DbView>();
		
		ARecordSet rs;

		String sqlStr = "select table_name from information_schema.tables " +
		"WHERE table_schema=? AND table_type='VIEW' " +
		"order by table_name;";

		rs = connection.exec(sqlStr, schema.getName());

		while (rs.next())
		{
			results.add(new DbView(rs.get("table_name"), schema));
		}
		
		return results;
	}
	
}
