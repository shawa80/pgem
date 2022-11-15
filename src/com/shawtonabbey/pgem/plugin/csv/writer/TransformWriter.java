package com.shawtonabbey.pgem.plugin.csv.writer;

import java.util.List;
import java.util.stream.IntStream;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.table.DbTable;

public class TransformWriter {

	public static String write(DbTable table, List<DbColumn> cols, String sql) {

		var code = "";

		code += "import java.sql.*;\n";
		code += "import com.shawtonabbey.pgem.plugin.csv.models.*;\n";
		code += "\n";
		code += "public class Import implements TableImporter {\n";
		code += "	\n";
		code += "	public PreparedStatement init(Connection db) throws SQLException {\n";
		code += "		\n";
		code += "		PreparedStatement stm = db.prepareStatement(\n";
		code += 			sql + "\"\n";
		code += "		);\n";
		code += "		\n";
		code += "		return stm;\n";
		code += "	}\n";
		code += "	\n";
		code += "	public void map(PreparedStatement stm, Object obj) throws SQLException {\n";
		code += "		Csv csv = (Csv)obj;\n\n";
		code += 		getParams(table, cols);
		code += "	}\n";
		code += "}\n";

		
		return code;
	}

	private static String getParams(DbTable table, List<DbColumn> cols) {
		
		var params = IntStream.range(0, cols.size())
		.mapToObj(i -> "		stm.setObject(" + (i+1) + ", csv." + cols.get(i).getName() + ");\n")
		.toArray(String[]::new);

		var p = String.join("", params);

		return p;
	}
}
