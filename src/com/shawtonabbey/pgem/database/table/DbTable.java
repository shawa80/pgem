package com.shawtonabbey.pgem.database.table;

import java.io.IOException;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

public class DbTable implements DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	

	@Getter
	private long oid;
	
	public DbTable(String name, DbSchema schema, Long oid) throws IOException {
		this.name = name;
		this.schema = schema;
		this.oid = oid;
	}
	
	
}
