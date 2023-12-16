package com.shawtonabbey.pgem.database.primary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public class DbPrimaryKey {
	
	public DbPrimaryKey() {
		columns = new ArrayList<DbPrimary>();
	}
	
	@Getter
	private List<DbPrimary> columns;
	
	public void add(DbPrimary column) {
		columns.add(column);
	}
	
	
	public String toString() {
		var cols = columns.stream().map(x-> x.getColname()).collect(Collectors.toList());
		
		return String.join(",", cols);
	}
}
