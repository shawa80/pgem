package com.shawtonabbey.pgem.database.rule;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbRule {

	@Getter
	private String name;
	@Getter 
	private String def;
	
	
}
