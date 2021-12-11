package com.shawtonabbey.pgem.database.ui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

public class Row {

	public List<Object> columns = new ArrayList<Object>();
	
	@Getter
	public int length;
	
	public Row(ResultSet row) throws SQLException {
		
		length = row.getMetaData().getColumnCount();
		
		for (var i = 1; i <= length; i++)
			columns.add(row.getObject(i));	
	}
	
	public Row(Object... rows) {
		for (var row : rows) {
			columns.add(row);
		}
	}

	public void add(Object col) {
		columns.add(col);
	}
	
	public Stream<Object> stream() {
		return columns.stream();
	}
	
	/**
	 * 
	 * @param index Zero based index
	 * @return
	 */
	public Object getColumn(int index) {
		return columns.get(index);
	}
	
}
