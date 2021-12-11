package com.shawtonabbey.pgem.plugin.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class XPathQueryParser {

	@Getter
	private String tableQuery;
	
	@Getter
	private List<ColumnQuery> columnQueries;
	
	public void parse(String xpath) {
		
		columnQueries = new ArrayList<ColumnQuery>();
		
		var xpaths = xpath.split("\n");
		tableQuery = xpaths[0];
		
		var colPaths = Arrays.stream(xpaths).skip(1).collect(Collectors.toList());

		for (int i = 0; i < colPaths.size(); i+=2)
		{
			var col = new ColumnQuery(colPaths.get(i), colPaths.get(i+1));
			
			columnQueries.add(col);
		}
		
	}
	
}
