package com.shawtonabbey.pgem.tree.trigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class TriggerPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(TriggerGroup.Ev.class);
		dispatch.register(TriggerInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(TableInstance.Ev.class).listen((s, e) -> {
			s.addNode(appContext.getBean(TriggerGroup.class, s).load(e));
		});
	}
}

