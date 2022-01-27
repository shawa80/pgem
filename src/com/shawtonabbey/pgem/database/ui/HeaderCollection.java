package com.shawtonabbey.pgem.database.ui;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class HeaderCollection extends ArrayList<Header> {
	

	private static final long serialVersionUID = 1L;

	public HeaderCollection(ResultSetMetaData data) throws SQLException {
	
		var length = data.getColumnCount();
		
		
		for (var i = 1; i <= length; i++) {
			
			var name = data.getColumnName(i);
			var type = data.getColumnClassName(i);
			
			add(new Header(name,type));
		}
		
	}
	
	public HeaderCollection(String... args) {
		
		for (var arg : args) {
			add(new Header(arg));
		}
		
	}

	public void add(String header) {
		add(new Header(header));
	}
	
	public HeaderCollection() {
		
	}
	

}
