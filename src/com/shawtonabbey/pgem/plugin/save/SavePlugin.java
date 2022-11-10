package com.shawtonabbey.pgem.plugin.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class SavePlugin extends PluginBase {
	
	@Autowired
	SaveAction save;
	
	@Autowired
	OpenAction open;


	
	public void init() {
				
		dispatch.find(AQueryWindow.Added.class).listen((m,ev) -> {
			
			m.addAction("Open", (e)->open.perform(m, m));
			m.addAction("Save", (e)->save.perform(m, m));	
		});
		
	}
	
}

