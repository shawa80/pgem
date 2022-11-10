package com.shawtonabbey.pgem.tree.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;
import com.shawtonabbey.pgem.tree.view.ViewGroup;

@Component
public class ViewPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(ViewGroup.Added.class);
		dispatch.register(ViewInstance.Added.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(ViewGroup.class, s).load(event));
		});
	}
}
