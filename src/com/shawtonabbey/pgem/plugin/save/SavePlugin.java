package com.shawtonabbey.pgem.plugin.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class SavePlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	SaveAction save;
	
	@Autowired
	OpenAction open;

	public void register() {
	}

	
	public void init() {
				
		dispatch.find(AQueryWindow.Ev.class).listen((m,ev) -> {
			
			m.addAction("Open", (e)->open.perform(m, m));
			m.addAction("Save", (e)->save.perform(m, m));	
		});
		
	}
	
}

