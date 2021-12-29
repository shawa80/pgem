package com.shawtonabbey.pgem.database;

import java.io.IOException;

public interface Definable {
	public String getDefinition(DBC connection) throws IOException;
}
