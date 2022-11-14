package com.shawtonabbey.pgem.database.table;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

public class DbTable implements DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	@Getter
	private List<DbColumn> columns;
	
	@Getter
	private long oid;
	
	public DbTable(DBC connection, String name, DbSchema schema, Long oid) throws IOException {
		this.name = name;
		this.schema = schema;
		
		this.columns = DbColumn.getColumns(connection, this);
		this.oid = oid;
	}
	
}
