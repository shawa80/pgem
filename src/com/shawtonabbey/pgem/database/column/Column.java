package com.shawtonabbey.pgem.database.column;

import lombok.Getter;
import lombok.Setter;

public class Column {

	@Getter @Setter
	private String column_name;
	
	@Getter @Setter
	private String data_type;
	
	@Getter @Setter
	private String column_default;
	
	@Getter @Setter
	private String is_nullable;

	@Getter @Setter
	private Integer character_maximum_length;
	
}
