package com.shawtonabbey.pgem.database.sequence;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbSchema;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbSequenceFactory {

	public List<DbSequence> getSequence(DBC connection, DbSchema dbSchema) throws IOException {

		var sqlStr = "select sequence_name " +
				    "from information_schema.sequences " +
					"where sequence_schema = ? " +
					"order by sequence_name;";

		var c = new Constr<>(DbSequence.class);
		var results = connection.execX(sqlStr, c, dbSchema.getName());
		
		return results;
	}
	
}
