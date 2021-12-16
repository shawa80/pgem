package com.shawtonabbey.pgem.tree.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class TablePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(TableGroup.Ev.class);
		dispatch.register(TableInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Ev.class).listen((s, e) -> {
			s.addNode(appContext.getBean(TableGroup.class, s).load(e));
		});
	}
}
