package com.shawtonabbey.pgem.plugin.xml;

import lombok.Getter;

public class ColumnQuery {

	@Getter
	private String colName;
	
	@Getter
	private String xpath;
	
	public ColumnQuery(String colName, String xpath) {
		
		this.colName = colName;
		this.xpath = xpath;
	}
}
