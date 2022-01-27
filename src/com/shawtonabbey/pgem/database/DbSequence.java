package com.shawtonabbey.pgem.database;

import java.io.IOException;
import java.util.List;

import com.shawtonabbey.pgem.database.deserializers.Constr;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class DbSequence {

	@Getter
	private String name;
		
	public static List<DbSequence> getSequence(DBC connection, DbSchema dbSchema) throws IOException {

		var sqlStr = "select sequence_name " +
				    "from information_schema.sequences " +
					"where sequence_schema = ? " +
					"order by sequence_name;";

		var c = new Constr<>(DbSequence.class);
		var results = connection.execX(sqlStr, c, dbSchema.getName());
		
		return results;
	}
	
}
