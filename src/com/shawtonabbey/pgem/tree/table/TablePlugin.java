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

	}
	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, e) -> {
			s.addNode(appContext.getBean(TableGroup.class, s).load(e));
		});
	}
}
