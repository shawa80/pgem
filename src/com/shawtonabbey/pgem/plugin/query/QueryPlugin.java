package com.shawtonabbey.pgem.plugin.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
public class QueryPlugin extends PluginBase {

	
	@Autowired
	private PgemMainWindow window;
	

	
	public void init() {
				
		
		dispatch.find(DatabaseInstance.Added.class).listen((m,ev) -> {
			
			m.addPopup("Query", (e) -> {

				window.launchQueryWin(m.findDbc(), "");
				
			});
		});
		
	}
	
}
