package com.shawtonabbey.pgem.database.routine;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.deserializers.Property;
import com.shawtonabbey.pgem.database.deserializers.TextValue;

@AllArgsConstructor
public class DbRoutine {

	@Getter
	private String name;
		
	@Getter
	private long oid;
	
	
	
	public String getReturnType(DBC connection) throws IOException {

		var sqlStr = "select pg_get_function_result as text_value "
				+ "from pg_get_function_result(?)";

		var c = new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);

		return rs.stream()
			.findFirst()
			.get().getText_value();
		
	}

	
	public List<String> getRoutinesParams(DBC connection) throws IOException {

		var sqlStr = "select pg_get_function_arguments as text_value "
				+ "from pg_get_function_arguments(?);";

		var c= new Property<>(TextValue.class);
		var rs = connection.execX(sqlStr, c, oid);

		
		var params = rs.stream()
			.findFirst()
			.get().getText_value()
			.split(",");
		
		return Stream.of(params)
			.map (elem -> elem.trim())
			.collect(Collectors.toList());
		
	}
	
}

