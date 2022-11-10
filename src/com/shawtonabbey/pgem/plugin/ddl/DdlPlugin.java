package com.shawtonabbey.pgem.plugin.ddl;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.TextValue;
import com.shawtonabbey.pgem.database.deserializers.Property;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.plugin.ddl.index.IndexCreatePanel;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.column.ColumnGroup;
import com.shawtonabbey.pgem.tree.column.ColumnInstance;
import com.shawtonabbey.pgem.tree.constraint.ConstraintInstance;
import com.shawtonabbey.pgem.tree.database.ServerInstance;
import com.shawtonabbey.pgem.tree.index.IndexGroup;
import com.shawtonabbey.pgem.tree.index.IndexInstance;
import com.shawtonabbey.pgem.tree.routine.RoutineGroup;
import com.shawtonabbey.pgem.tree.routine.RoutineInstance;
import com.shawtonabbey.pgem.tree.rule.RuleInstance;
import com.shawtonabbey.pgem.tree.sequence.SequenceGroup;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.trigger.TriggerInstance;
import com.shawtonabbey.pgem.tree.view.ViewGroup;
import com.shawtonabbey.pgem.tree.view.ViewInstance;
import com.shawtonabbey.pgem.ui.MainWindow;
import java.util.List;

@Component
public class DdlPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;
	@Autowired
	private MainWindow win;
	
	public void register() {
	}

	
	public void init() {

		dispatch.find(ServerInstance.Added.class).listen((srv, ev) -> {
			srv.addPopup("Reload", (e) -> {
				srv.reload(new Event());				
			});
		});
	
		dispatch.find(RoutineGroup.Added.class).listen((rtGrp, ev) -> {
			
			rtGrp.addPopup("DDL", "Create", (e) -> {
				win.launchQueryWin(rtGrp.findDbc(), 
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
		
		
		
		dispatch.find(RoutineInstance.Added.class).listen((rtn,ev) -> {
			
			rtn.addPopup("DDL", "Create Script", (e) -> {
				
				var sw = new SwingWorker<List<TextValue>>()
						.setWork(() -> {
							var connection = rtn.findDbc();
							var c = new Property<>(TextValue.class);
							var result = connection.execX("select pg_get_functiondef(oid) as text_value \r\n" + 
									"from pg_proc\r\n" + 
									"where proname = ?;", c, rtn.getName());
							return result;
						})
						.thenOnEdt((w) -> {
							win.launchQueryWin(rtn.findDbc(), w.get(0).getText_value());							
							
						});
				sw.start();
			});
			

		});
		
		dispatch.find(RoutineInstance.Added.class).listen((rtn,ev) -> {
			
			rtn.addPopup("DDL", "Drop", (e) -> {
				win.launchQueryWin(rtn.findDbc(), 
						"DROP FUNCTION [ IF EXISTS ] " + rtn.getRoutine().getName() + " [ ( [ [ argmode ] [ argname ] argtype [, ...] ] ) ] [, ...]\r\n" + 
						"    [ CASCADE | RESTRICT ]"
						);
			});
			

		});
		
		dispatch.find(SequenceGroup.Added.class).listen((sqc, ev) -> {
			
			sqc.addPopup("DDL", "Create", (e) -> {
				win.launchQueryWin(sqc.findDbc(), 
						"CREATE [ TEMPORARY | TEMP ] SEQUENCE [ IF NOT EXISTS ] name [ INCREMENT [ BY ] increment ]\r\n" + 
						"    [ MINVALUE minvalue | NO MINVALUE ] [ MAXVALUE maxvalue | NO MAXVALUE ]\r\n" + 
						"    [ START [ WITH ] start ] [ CACHE cache ] [ [ NO ] CYCLE ]\r\n" + 
						"    [ OWNED BY { table_name.column_name | NONE } ]"
						);
			});
		});
		
		dispatch.find(TableGroup.Added.class).listen((group, ev) -> {
			
			group.addPopup("DDL", "New Table", (e) -> {
				
				win.launchQueryWin(group.findDbc(), 
						"CREATE TABLE <table> (\r\n" + 
						"    <col0>      <type> PRIMARY KEY,\r\n" + 
						"    <col1>      <type> NOT NULL,\r\n" + 
						");");
			});

		});
		
		dispatch.find(TableInstance.Added.class).listen((table,ev) -> {
			
			table.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(table.findDbc(), "drop table " + table.getName() + ";");
			});

			
			table.addPopup("DDL", "Primary Key", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> "--" + d.getName())
						.collect(Collectors.joining("\n"));
				
				win.launchQueryWin(table.findDbc(), columns + "\nALTER TABLE " 
				+ table.getName() + " ADD PRIMARY KEY (<column>);");
				
			});

		});

		
		
		dispatch.find(ColumnGroup.Added.class).listen((grp, ev) -> {
			
			if (!(grp.parent instanceof TableInstance))
				return;

			var table = (TableInstance)grp.parent;
			
			grp.addPopup("DDL", "Add", (e) -> {
				

				win.launchQueryWin(table.findDbc(), 
						"ALTER TABLE " + table.getTable().getName() + "\r\n" + 
						"add COLUMN <name> <type>;");
				
			});
		});
		
		dispatch.find(ColumnInstance.Ev.class).listen((cln,ev)-> {
			
			if (!(cln.getUiParent().getUiParent() instanceof TableInstance))
				return;
			var table = (DbTable)cln.getColumn().getParent();
			
			cln.addPopup("DDL", "Drop", (e) -> {
								
				win.launchQueryWin(cln.findDbc(), 
						"ALTER TABLE " + table.getName() + "\r\n" + 
						"DROP COLUMN " + cln.getColumn().getName() + " CASCADE;");
				
			});
		});
	
		dispatch.find(ViewGroup.Added.class).listen((view,ev) -> {
			view.addPopup("DDL", "Create", (e) -> {
				
				win.launchQueryWin(view.findDbc(), 
						"CREATE [ OR REPLACE ] [ TEMP | TEMPORARY ] VIEW name [ ( column_name [, ...] ) ]\r\n" + 
						"    [ WITH ( view_option_name [= view_option_value] [, ... ] ) ]\r\n" + 
						"    AS query;");
			});
		});
		
		
		dispatch.find(ViewInstance.Added.class).listen((view,ev) -> {
			
			view.addPopup("DDL", "Script Create", (e) -> {
				
				try {
				var query = view.getView().getDefinition(view.findDbc());
				
				win.launchQueryWin(view.findDbc(), query);
				} catch (Exception ex) {}
			});
			
			view.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(view.findDbc(), "drop view " + view.getView().getName() + ";");
			});		
		});
		

		dispatch.find(IndexGroup.Added.class).listen((indexGrp,ev) -> {
			
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
				
				win.launchQueryWin(indexGrp.findDbc(), query);
			});
		});
		
		dispatch.find(IndexInstance.Added.class).listen((index,ev) -> {
			
			index.addPopup("DDL", "Script Create", (e) -> {
				
				var query = index.getIndex().getDef();
				
				win.launchQueryWin(index.findDbc(), query);
			});
			
			index.addPopup("DDL", "Drop", (e) -> {
				
				win.launchQueryWin(index.findDbc(), "drop index " + index.getIndex().getName() + ";");
			});		
		});

		dispatch.find(ConstraintInstance.Added.class).listen((con,ev) -> {
			
			con.addPopup("DDL", "Script Create", (e) -> {
				
				var sw = new SwingWorker<String>()
						.setWork(() -> con.getCon().getDefinition(con.findDbc()))
						.thenOnEdt((q) -> {
							win.launchQueryWin(con.findDbc(), q);
													
						});
				sw.start();
			});
				
		});
		
		dispatch.find(RuleInstance.Added.class).listen((rule,ev) -> {
			
			rule.addPopup("DDL", "Script Create", (e) -> {
				
				var sw = new SwingWorker<String>()
						.setWork(() -> rule.getRule().getDef())
						.thenOnEdt((q) -> {
							win.launchQueryWin(rule.findDbc(), q);
													
						});
				sw.start();
			});
				
		});

		dispatch.find(TriggerInstance.Added.class).listen((trigger,ev) -> {
			
			trigger.addPopup("DDL", "Script Create", (e) -> {
				
				var sw = new SwingWorker<String>()
						.setWork(() -> trigger.getTrigger().getDef(trigger.findDbc()))
						.thenOnEdt((q) -> {
							win.launchQueryWin(trigger.findDbc(), q);
													
						});
				sw.start();
			});
				
		});
	
	}
	
}
