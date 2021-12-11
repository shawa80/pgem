package com.shawtonabbey.pgem.plugin.csv.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface TableImporter {

	public PreparedStatement init(Connection db) throws SQLException;
	
	public void map(PreparedStatement stm, Object obj) throws SQLException;
}

