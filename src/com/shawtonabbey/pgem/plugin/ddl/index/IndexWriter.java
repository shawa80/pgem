package com.shawtonabbey.pgem.plugin.ddl.index;

import java.util.stream.Collectors;

public class IndexWriter {

	
	public static String write(IndexModel model) {
		
		String result = "";
		String unique = model.isUnique() ? "UNIQUE " : "";
		String concurrently = model.isConcurrently() ? "CONCURRENTLY " : "";
		
		result = "create " + unique + "index " + concurrently;
		result += "[" + model.getIndexName() + "] on " + model.getTableName();
		var colList = model.getColumns().stream().map(c-> c.getName()).collect(Collectors.joining( "," ));
		result += " ("+ colList +");";
		
		return result;
	}
	
}
