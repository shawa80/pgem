package com.shawtonabbey.pgem.tree.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.view.ViewGroup;

@Component
public class ViewPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		dispatch.schema.listen((s, event) -> {
			s.addNode(appContext.getBean(ViewGroup.class, s).load(event));
		});
	}
}
