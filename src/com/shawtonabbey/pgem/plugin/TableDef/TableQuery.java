package com.shawtonabbey.pgem.plugin.TableDef;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class TableQuery {

	@Getter @Setter
	Integer relchecks;

	@Getter @Setter
	String relkind;

	@Getter @Setter
	Boolean relhasindex;

	@Getter @Setter
	Boolean relhasrules;

	@Getter @Setter
	Boolean relhastriggers;

	@Getter @Setter
	Boolean relrowsecurity;

	@Getter @Setter
	Boolean relforcerowsecurity;

	@Getter @Setter
	Boolean relhasoids;

	@Getter @Setter
	Boolean relispartition;

	@Getter @Setter
	Long oid;

	@Getter @Setter
	Long reltablespace;

	@Getter @Setter
	String reloftype;

	@Getter @Setter
	String relpersistence;

	@Getter @Setter
	String relreplident;

	@Getter @Setter
	String amname;

}