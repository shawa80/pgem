package com.shawtonabbey.pgem.plugin.csv.writer;

import java.util.List;
import java.util.stream.Collectors;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.table.DbTable;

public class SqlWriter {

	
	public static String write(DbTable table, List<DbColumn> cols) {

		var columns = cols.stream().map(d-> d.getName()).collect(Collectors.joining(", "));
		var values = cols.stream().map(d-> "?").collect(Collectors.joining(", "));
		
		var query = "";
		query += "			\"insert into " + table.getName() + " \" +\n";
		query += "			\"(" + columns + ") \" +\n";
		query += "			\"values (" + values +");";
		
		return query;
	}

	
}
