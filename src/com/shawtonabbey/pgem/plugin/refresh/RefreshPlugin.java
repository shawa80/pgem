package com.shawtonabbey.pgem.plugin.refresh;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.view.ViewGroup;

@Component
public class RefreshPlugin extends PluginBase {

	
	public void init() {
				
		
		dispatch.find(ViewGroup.Added.class).listen((m,ev) -> {
			
			m.addPopup("Refresh", (e) -> {
				
				var event = new Event();
				m.refresh(event);
				
			});
		});
		
		dispatch.find(TableGroup.Added.class).listen((m,ev) -> {
			
			m.addPopup("Refresh", (e) -> {
				
				var event = new Event();
				m.refresh(event);
				
			});
		});
		
	}
	
}
