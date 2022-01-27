package com.shawtonabbey.pgem.database.ui;

import lombok.Getter;

public class Header {

	
	@Getter
	private String name;
	
	@Getter
	private String className;
	
	public Header(String name) {
		this.name = name;
		className = "";
	}
	
	public Header(String name, String className) {
		this.name = name;
		this.className = className;
	}
}
