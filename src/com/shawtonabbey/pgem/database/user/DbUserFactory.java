package com.shawtonabbey.pgem.database.user;

import java.io.IOException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.database.deserializers.Constr;

@Component
@Scope("singleton")
public class DbUserFactory {

	public List<DbUser> getUsers(DBC connection, DbDatabase database) throws IOException {

		String sqlStr = "SELECT usename " + 
				"FROM pg_catalog.pg_user";

		var c = new Constr<>(DbUser.class);
		var results = connection.execX(sqlStr, c);

		return results;
	}	
	
}
