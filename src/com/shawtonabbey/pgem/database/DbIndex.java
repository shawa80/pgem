package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class DbIndex implements DbcProvider {

	@Getter
	private String name;
	//private DbTable table;
	@Getter
	private DBC connection;
	@Getter
	private String def;
	
	private DbIndex(String name, DbTable table, DBC connection, String def) {
		this.name = name;
		//this.table = table;
		this.def = def;
		this.connection = connection;
	}
	


	public static List<DbIndex> getIndexes(DbTable table) throws IOException {
		
		var results = new ArrayList<DbIndex>();
		
		ARecordSet rs;

		var sqlStr = "select indexname, indexdef " + 
		"from pg_indexes " +
		" where schemaname = ? " +
		" and tablename = ?";

		rs = table.getDbInstance().exec(sqlStr, table.getSchema().getName(), table.getName());

		if (rs != null) {
			
			while (rs.next())
			{
					results.add(new DbIndex(rs.get("indexname"), table, table.getDbInstance(), rs.get("indexdef")));
			}
			rs.close();
		}
		
		return results;
	}

	@Override
	public DBC getDbInstance() {
		return connection;
	}


}
