package com.shawtonabbey.pgem.database.table;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

@Component
@Scope("prototype")
public class DbTable implements DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	

	@Getter
	private long oid;
	
	@Autowired
	public DbTable(String name, DbSchema schema, Long oid) throws IOException {
		this.name = name;
		this.schema = schema;
		this.oid = oid;
	}
	
	
}
