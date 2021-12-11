package com.shawtonabbey.pgem.plugin.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class QueryPlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	@Autowired
	private PgemMainWindow window;
	
	public void init() {
				
		
		dispatch.databaseListener.getMaint().add((m,ev) -> {
			
			m.addPopup("Query", (e) -> {

				window.launchQueryWin(m.getDatabase().getDbInstance(), "");
				
			});
		});
		
	}
	
}
