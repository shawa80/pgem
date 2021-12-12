package com.shawtonabbey.pgem.plugin.ddl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.plugin.ddl.index.IndexCreatePanel;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class DdlPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;
	@Autowired
	private MainWindow win;
	
	
	public void init() {

		dispatch.server.listen((srv, ev) -> {
			srv.addPopup("Reload", (e) -> {
				srv.reload(new Event());				
			});
		});
	
		dispatch.routineGroup.listen((rtGrp, ev) -> {
			
			rtGrp.addPopup("DDL", "Create", (e) -> {
				win.launchQueryWin(rtGrp.getParentDb().getSchema().getDbInstance(), 
						"CREATE [ OR REPLACE ] FUNCTION\r\n" + 
						"    name ( [ [ argmode ] [ argname ] argtype [ { DEFAULT | = } default_expr ] [, ...] ] )\r\n" + 
						"    [ RETURNS rettype\r\n" + 
						"      | RETURNS TABLE ( column_name column_type [, ...] ) ]\r\n" + 
						"  { LANGUAGE lang_name\r\n" + 
						"    | WINDOW\r\n" + 
						"    | IMMUTABLE | STABLE | VOLATILE\r\n" + 
						"    | CALLED ON NULL INPUT | RETURNS NULL ON NULL INPUT | STRICT\r\n" + 
						"    | [ EXTERNAL ] SECURITY INVOKER | [ EXTERNAL ] SECURITY DEFINER\r\n" + 
						"    | COST execution_cost\r\n" + 
						"    | ROWS result_rows\r\n" + 
						"    | SET configuration_parameter { TO value | = value | FROM CURRENT }\r\n" + 
						"    | AS 'definition'\r\n" + 
						"    | AS 'obj_file', 'link_symbol'\r\n" + 
						"  } ...\r\n" + 
						"    [ WITH ( attribute [, ...] ) ]"
						);
			});
		});
		
		
		dispatch.routine.listen((rtn,ev) -> {
			
			rtn.addPopup("DDL", "Drop", (e) -> {
				win.launchQueryWin(rtn.getParentDb().getParentDb().getSchema().getDbInstance(), 
						"DROP FUNCTION [ IF EXISTS ] " + rtn.getRoutine().getName() + " [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ] [, ...]\r\n" + 
						"    [ CASCADE | RESTRICT ]"
						);
			});
			

		});
		
		dispatch.sequenceGroup.listen((sqc, ev) -> {
			
			sqc.addPopup("DDL", "Create", (e) -> {
				win.launchQueryWin(sqc.getParentDb().getSchema().getDbInstance(), 
						"CREATE [ TEMPORARY | TEMP ] SEQUENCE [ IF NOT EXISTS ] name [ INCREMENT [ BY ] increment ]\r\n" + 
						"    [ MINVALUE minvalue | NO MINVALUE ] [ MAXVALUE maxvalue | NO MAXVALUE ]\r\n" + 
						"    [ START [ WITH ] start ] [ CACHE cache ] [ [ NO ] CYCLE ]\r\n" + 
						"    [ OWNED BY { table_name.column_name | NONE } ]"
						);
			});
		});
		
		dispatch.tableGroup.listen((group, ev) -> {
			
			group.addPopup("DDL", "New Table", (e) -> {
				
				win.launchQueryWin(group.getParentDb().getSchema().getDbInstance(), 
						"CREATE TABLE <table> (\r\n" + 
						"    <col0>      <type> PRIMARY KEY,\r\n" + 
						"    <col1>      <type> NOT NULL,\r\n" + 
						");");
			});

		});
		
		dispatch.table.listen((table,ev) -> {
			
			table.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(table.getTable().getDbInstance(), "drop table " + table.getName() + ";");
			});

			
			table.addPopup("DDL", "Primary Key", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> "--" + d.getName())
						.collect(Collectors.joining("\n"));
				
				win.launchQueryWin(table.getTable().getDbInstance(), columns + "\nALTER TABLE " 
				+ table.getName() + " ADD PRIMARY KEY (<column>);");
				
			});

		});

		
		
		dispatch.columnGroup.listen((grp, ev) -> {
			
			if (!(grp.parent instanceof TableInstance))
				return;

			var table = (TableInstance)grp.parent;
			
			grp.addPopup("DDL", "Add", (e) -> {
				

				win.launchQueryWin(table.getTable().getDbInstance(), 
						"ALTER TABLE " + table.getTable().getName() + "\r\n" + 
						"add COLUMN <name> <type>;");
				
			});
		});
		
		dispatch.column.listen((cln,ev)-> {
			
			if (!(cln.getUiParent().getUiParent() instanceof TableInstance))
				return;
			var table = (DbTable)cln.getColumn().getParent();
			
			cln.addPopup("DDL", "Drop", (e) -> {
								
				win.launchQueryWin(cln.getColumn().getDbInstance(), 
						"ALTER TABLE " + table.getName() + "\r\n" + 
						"DROP COLUMN " + cln.getColumn().getName() + " CASCADE;");
				
			});
		});
	
		dispatch.viewGroup.listen((view,ev) -> {
			view.addPopup("DDL", "Create", (e) -> {
				
				win.launchQueryWin(view.getParentDb().getSchema().getDbInstance(), 
						"CREATE [ OR REPLACE ] [ TEMP | TEMPORARY ] VIEW name [ ( column_name [, ...] ) ]\r\n" + 
						"    [ WITH ( view_option_name [= view_option_value] [, ... ] ) ]\r\n" + 
						"    AS query;");
			});
		});
		
		
		dispatch.view.listen((view,ev) -> {
			
			view.addPopup("DDL", "Script Create", (e) -> {
				
				try {
				var query = view.getView().getDefinition();
				
				win.launchQueryWin(view.getView().getDbInstance(), query);
				} catch (Exception ex) {}
			});
			
			view.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(view.getView().getDbInstance(), "drop view " + view.getView().getName() + ";");
			});		
		});
		

		dispatch.indexGroup.listen((indexGrp,ev) -> {
			
			indexGrp.addPopup("DDL", "GUI", (e) -> {
				var ui = new IndexCreatePanel(indexGrp.getTable());

				win.launchPanel(ui);
				
			});
			
			indexGrp.addPopup("DDL",  "Create", (e) -> {
				var query = "CREATE [ UNIQUE ] INDEX [ CONCURRENTLY ] [ name ] ON table [ USING method ]\r\n" + 
						"    ( { column | ( expression ) } [ COLLATE collation ] [ opclass ] [ ASC | DESC ] [ NULLS { FIRST | LAST } ] [, ...] )\r\n" + 
						"    [ WITH ( storage_parameter = value [, ... ] ) ]\r\n" + 
						"    [ TABLESPACE tablespace ]\r\n" + 
						"    [ WHERE predicate ]";
				
				win.launchQueryWin(indexGrp.getTable().getDbInstance(), query);
			});
		});
		
		dispatch.index.listen((index,ev) -> {
			
			index.addPopup("DDL", "Script Create", (e) -> {
				
				var query = index.getIndex().getDef();
				
				win.launchQueryWin(index.getIndex().getDbInstance(), query);
			});
			
			index.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(index.getIndex().getDbInstance(), "drop index " + index.getIndex().getName() + ";");
			});		
		});

	
	}
	
}
