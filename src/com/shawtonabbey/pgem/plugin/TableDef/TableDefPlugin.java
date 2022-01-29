package com.shawtonabbey.pgem.plugin.TableDef;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.database.deserializers.Constr;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class TableDefPlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	private PgemMainWindow window;
	
	public void register() {
	}

	
	public void init() {
				
		dispatch.find(TableInstance.Ev.class).listen((t,ev) -> {
			
			t.addPopup("Def", (e) -> {

				//tODO move to thread;
				var dbc = t.findDbc();
				//var oid = t.getTable().getOid();
				var oid = 7;
				
				var c = new Constr<>(TableQuery.class);
				try {
					dbc.execX("SELECT c.relchecks, c.relkind, c.relhasindex, c.relhasrules, \n" + 
							"c.relhastriggers, c.relrowsecurity, c.relforcerowsecurity, \n" + 
							"false AS relhasoids, c.relispartition, c.oid, c.reltablespace, \n" + 
							"CASE WHEN c.reloftype = 0 THEN '' ELSE c.reloftype::pg_catalog.regtype::pg_catalog.text END,\n" + 
							"c.relpersistence, c.relreplident, am.amname\n" + 
							"FROM pg_catalog.pg_class c\n" + 
							"LEFT JOIN pg_catalog.pg_class tc ON (c.reltoastrelid = tc.oid)\n" + 
							"LEFT JOIN pg_catalog.pg_am am ON (c.relam = am.oid)"
							+ "WHERE c.oid = ?", c, oid);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				window.launchQueryWin(t.findDbc(), "");
				
			});
			
		});
		
	}
	
}

