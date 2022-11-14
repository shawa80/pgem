package com.shawtonabbey.pgem.database.column;

import com.shawtonabbey.pgem.database.DbTableLike;
import lombok.Getter;

public class DbColumn {
	
	@Getter
	private String name;

	@Getter
	private String type;
	
	@Getter
	private DbTableLike parent;
	
	@Getter
	private boolean isNullable;
	
	@Getter
	private String defaultValue;
		
	@Getter
	private Integer characterMaximumLength;
	
	public DbColumn(DbTableLike parent, Column c) {
				
		this(parent, c.getColumn_name(), 
				c.getData_type(), 
				"YES".equals(c.getIs_nullable()), 
				c.getColumn_default(),
				c.getCharacter_maximum_length());
	}
	
	public DbColumn(DbTableLike parent, String name, String type, boolean isNullable, String defaultValue,
			Integer characterMaximumLength) {
		
		this.name = name;
		this.type = type;
		this.parent = parent;
		this.isNullable = isNullable;
		this.defaultValue = defaultValue;
		this.characterMaximumLength = characterMaximumLength;
	}

	
	
	@Override
	public String toString() {
		return name;
	}
}
