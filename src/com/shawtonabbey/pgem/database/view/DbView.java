package com.shawtonabbey.pgem.database.view;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.TextValue;
import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnFactory;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

@Component
@Scope("prototype")
public class DbView implements DbColumnCollection, DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;
		
	private DBC connection;
	
	@Autowired
	private DbColumnFactory factory;
	
	public DbView (DBC connection, String name, DbSchema schema) throws IOException {
		
		this.name = name;
		this.schema = schema;
		this.connection = connection;
	}
	
	public List<DbColumn> getColumns()  {
		try { 
			return factory.getColumns(connection, this);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public String getDefinition(DBC connection) throws IOException {
		
		var result = "create view " + schema.getName()  + "." + name + "\nas\n" ;
		
		var sql = "select view_definition as text_value from information_schema.views where table_name = ?;";
				
		var def = connection.first(sql, TextValue.class, name);
			
		return result + def.getText_value();
	}
	
}
