package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class SequencePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(SequenceGroup.Added.class);
		dispatch.register(SequenceInstance.Added.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(SequenceGroup.class, s).load(event));
		});
	}
}
