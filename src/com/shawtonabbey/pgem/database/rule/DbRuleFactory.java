package com.shawtonabbey.pgem.database.rule;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbRuleFactory {

	public List<DbRule> getRules(DBC connection, DbTableLike table) throws IOException {
			
		
		var sqlStr = "select rulename as name, definition as def "
				+ " from pg_rules"
				+ " where schemaname = ?"
				+ " and tablename = ?;";

		var c = new Constr<>(DbRule.class);
		var results = connection.execX(sqlStr, c,
				table.getSchema().getName(), table.getName());

		
		return results;
	}
	
}
