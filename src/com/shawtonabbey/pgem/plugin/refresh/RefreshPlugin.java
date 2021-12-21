package com.shawtonabbey.pgem.plugin.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.view.ViewGroup;

@Component
public class RefreshPlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	
	public void register() {
	}

	
	public void init() {
				
		
		dispatch.find(ViewGroup.Ev.class).listen((m,ev) -> {
			
			m.addPopup("Refresh", (e) -> {
				
				var event = new Event();
				m.refresh(event);
				
			});
		});
		
		dispatch.find(TableGroup.Ev.class).listen((m,ev) -> {
			
			m.addPopup("Refresh", (e) -> {
				
				var event = new Event();
				m.refresh(event);
				
			});
		});
		
	}
	
}
