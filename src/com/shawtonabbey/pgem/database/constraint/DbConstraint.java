package com.shawtonabbey.pgem.database.constraint;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.TextValue;
import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.database.deserializers.Property;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbConstraint {

	@Getter
	private String name;
		
	@Getter
	private Long oid;
	
	
	public String getDefinition(DBC connection) throws IOException {
		
		var sqlStr = "select pg_get_constraintdef as text_value "
				+ "from pg_get_constraintdef(?, true);";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);
		
		return rs.stream().findFirst().orElseThrow().getText_value();
	}
	
}

