package com.shawtonabbey.pgem.database.index;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbIndex {

	@Getter
	private String name;
	@Getter
	private String def;
}
