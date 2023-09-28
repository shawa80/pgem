package com.shawtonabbey.pgem.plugin.sqlhint;

import lombok.Getter;

public class Selection {

	@Getter
	private int start;
	@Getter
	private int length;
	
	public Selection(int start, int length) {
		this.start = start;
		this.length = length;
	}
}
