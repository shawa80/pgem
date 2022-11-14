package com.shawtonabbey.pgem.database.trigger;

import java.io.IOException;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Property;
import com.shawtonabbey.pgem.database.deserializers.TextValue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbTrigger {

	@Getter
	private String name;
	@Getter
	private Long oid;
	
	
	public String getDef(DBC connection) throws IOException {

		var sqlStr = "select pg_get_triggerdef as text_value "
				+ "from pg_get_triggerdef(?, true);";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);
		
		var def = rs.stream()
			.findFirst()
			.get().getText_value();
		
		return def;
	}
	

}

