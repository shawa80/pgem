package com.shawtonabbey.pgem.plugin.TableDef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
 public class TableCols {

	@Getter @Setter
	String attname;

	@Getter @Setter
	String format_type;

	@Getter @Setter
	String pg_get_expr;

	@Getter @Setter
	Boolean attnotnull;

	@Getter @Setter
	String attcollation;

	@Getter @Setter
	String attidentity;

	@Getter @Setter
	String attgenerated;

}