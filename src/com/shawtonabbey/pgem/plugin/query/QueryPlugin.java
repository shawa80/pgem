package com.shawtonabbey.pgem.plugin.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
public class QueryPlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	@Autowired
	private PgemMainWindow window;
	
	public void register() {
	}

	
	public void init() {
				
		
		dispatch.find(DatabaseInstance.Ev.class).listen((m,ev) -> {
			
			m.addPopup("Query", (e) -> {

				window.launchQueryWin(m.getDatabase().getDbInstance(), "");
				
			});
		});
		
	}
	
}
