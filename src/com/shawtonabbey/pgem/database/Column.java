package com.shawtonabbey.pgem.database;

import lombok.Getter;
import lombok.Setter;

public class Column {

	@Getter @Setter
	private String column_name;
	
	
	@Getter @Setter
	private String data_type;

	
}
