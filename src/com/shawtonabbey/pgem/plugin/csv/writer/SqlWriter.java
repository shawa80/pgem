package com.shawtonabbey.pgem.plugin.csv.writer;

import java.util.stream.Collectors;

import com.shawtonabbey.pgem.database.table.DbTable;

public class SqlWriter {

	
	public static String write(DbTable table) {

		var columns = table.getColumns().stream().map(d-> d.getName()).collect(Collectors.joining(", "));
		var values = table.getColumns().stream().map(d-> "?").collect(Collectors.joining(", "));
		
		var query = "";
		query += "			\"insert into " + table.getName() + " \" +\n";
		query += "			\"(" + columns + ") \" +\n";
		query += "			\"values (" + values +");";
		
		return query;
	}

	
}
