package com.shawtonabbey.pgem.database.foreign;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbForeign {

	@Getter
	private String name;
	
	@Getter
	private String primary_schema;
	
	@Getter 
	private String primary_table;
	
	@Getter
	private String primary_column;
	
	@Getter
	private String foreign_table_schema;
	
	@Getter
	private String foreign_table_name;
	
	@Getter
	private String foreign_column_name;
	
}
