package com.shawtonabbey.pgem.database.view;

import java.io.IOException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.deserializers.TextValue;
import com.shawtonabbey.pgem.database.schema.DbSchema;

import lombok.Getter;

@Component
@Scope("prototype")
public class DbView implements DbTableLike {

	@Getter
	private String name;
	@Getter
	private DbSchema schema;
			
	public DbView (DBC connection, String name, DbSchema schema) throws IOException {
		
		this.name = name;
		this.schema = schema;
	}
	
	
	public String getDefinition(DBC connection) throws IOException {
		
		var result = "create view " + schema.getName()  + "." + name + "\nas\n" ;
		
		var sql = "select view_definition as text_value from information_schema.views where table_name = ?;";
				
		var def = connection.first(sql, TextValue.class, name);
			
		return result + def.getText_value();
	}
	
}
