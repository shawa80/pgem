package com.shawtonabbey.pgem.plugin.csv.models;

import java.util.Arrays;
import java.util.List;

public class CsvRow {
	
	private List<String> columns;
	
	
	public CsvRow(String line) {
		
		var cls = line.split(",");
		
		columns = Arrays.asList(cls);
		
	}
	
	public int getSize() {
		return columns.size();
	}
	
	public String getValue(int column) {
		return columns.get(column);
	}
	
	
}
