module pgem {


	opens com.shawtonabbey.pgem;
	opens com.shawtonabbey.pgem.ui.tree;
	opens com.shawtonabbey.pgem.tree.constraint;
	opens com.shawtonabbey.pgem.plugin.csv.ui;
	opens com.shawtonabbey.pgem.tree.user;
	opens com.shawtonabbey.pgem.plugin;
	opens com.shawtonabbey.pgem.database.ui;
	opens com.shawtonabbey.pgem.plugin.TableDef;
	opens com.shawtonabbey.pgem.tree.routine;
	opens com.shawtonabbey.pgem.tree.view;
	opens com.shawtonabbey.pgem.plugin.export;
	opens com.shawtonabbey.pgem.plugin.refresh;
	opens com.shawtonabbey.pgem.swingUtils;
	opens com.shawtonabbey.pgem.tree.schema;
	opens com.shawtonabbey.pgem.plugin.ddl;
	opens com.shawtonabbey.pgem.database;
	opens com.shawtonabbey.pgem.plugin.csv.writer;
	opens com.shawtonabbey.pgem.tree.sequence;
	opens com.shawtonabbey.pgem.tree.trigger;
	opens com.shawtonabbey.pgem.event;
	opens com.shawtonabbey.pgem.plugin.csv.models;
	opens com.shawtonabbey.pgem.plugin.save;
	opens com.shawtonabbey.pgem.plugin.connect;
	opens com.shawtonabbey.pgem.tree.table;
	opens com.shawtonabbey.pgem.query;
	opens com.shawtonabbey.pgem.ui.lambda;
	opens com.shawtonabbey.pgem.plugin.crud;
	opens com.shawtonabbey.pgem.tree.column;
	opens com.shawtonabbey.pgem.tree.database;
	opens com.shawtonabbey.pgem.plugin.query;
	opens com.shawtonabbey.pgem.plugin.xml;
	opens com.shawtonabbey.pgem.plugin.user;
	opens com.shawtonabbey.pgem.tree;
	opens com.shawtonabbey.pgem.plugin.debug;
	opens com.shawtonabbey.pgem.database.deserializers;
	opens com.shawtonabbey.pgem.plugin.JavaDef;
	opens com.shawtonabbey.pgem.plugin.ddl.index;
	opens com.shawtonabbey.pgem.tree.rule;
	opens com.shawtonabbey.pgem.tree.index;
	opens com.shawtonabbey.pgem.plugin.csv;
	opens com.shawtonabbey.pgem.ui;

	requires java.compiler;
	requires java.desktop;
	requires java.prefs;
	requires java.sql;
	requires java.xml;
	requires jdk.security.auth;
	requires static lombok;
	requires rsyntaxtextarea;
	requires spring.beans;
	requires spring.context;
	requires spring.core;
}