package com.shawtonabbey.pgem.database.view;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.TextValue;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

public class DbView implements DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;
		
	@Getter
	private List<DbColumn> columns;
	
	
	public DbView (DBC connection, String name, DbSchema schema) throws IOException {
		
		this.name = name;
		this.schema = schema;
		
		this.columns = DbColumn.getColumns(connection, this);
	}
	
	public String getDefinition(DBC connection) throws IOException {
		
		var result = "create view " + schema.getName()  + "." + name + "\nas\n" ;
		
		var sql = "select view_definition as text_value from information_schema.views where table_name = ?;";
				
		var def = connection.first(sql, TextValue.class, name);
			
		return result + def.getText_value();
	}
	
}
