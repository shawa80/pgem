package com.shawtonabbey.pgem.database.ui;

import lombok.Getter;

public class Status {

	@Getter
	private String message;
	
	@Getter
	private int count;
	
	public Status(String message) {
		
		this.message = message;
		count= 0;
	}
	
	public Status(String message, int count) {
		this.message = message;
		this.count = count;
	}
}
