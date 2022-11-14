package com.shawtonabbey.pgem.database.table;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnFactory;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

@Component
@Scope("prototype")
public class DbTable implements DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;	
	
	private List<DbColumn> columns;
	
	private DBC connection;
	
	@Autowired
	private DbColumnFactory factory;
	
	@Getter
	private long oid;
	
	public DbTable(DBC connection, String name, DbSchema schema, Long oid) throws IOException {
		this.name = name;
		this.schema = schema;
		this.connection = connection;
		this.oid = oid;
	}
	
	public List<DbColumn> getColumns()  {
		try { 
			return factory.getColumns(connection, this);
		} catch (Exception ex) {
			return null;
		}
	}
	
}
