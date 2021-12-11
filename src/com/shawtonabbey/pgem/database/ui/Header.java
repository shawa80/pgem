package com.shawtonabbey.pgem.database.ui;

import lombok.Getter;

public class Header {

	
	@Getter
	private String name;
	
	
	public Header(String name) {
		this.name = name;
	}
}
