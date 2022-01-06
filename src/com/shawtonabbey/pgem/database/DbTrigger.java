package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbTrigger {

	@Getter
	private String name;
	
		
	public static List<DbTrigger> getTriggers(DBC connection, DbTable table) throws IOException {

		List<DbTrigger> results = new ArrayList<DbTrigger>();
		
		ARecordSet rs;

		var sqlStr = "select trigger_name " + 
				"from information_schema.triggers " + 
				"where trigger_schema = ? " + 
				"and event_object_table = ? ";

		rs = connection.exec(sqlStr, table.getSchema().getName(), table.getName());

		while (rs.next())
		{
			results.add(new DbTrigger(rs.get("trigger_name")));
		}
		
		return results;
	}
	


}

