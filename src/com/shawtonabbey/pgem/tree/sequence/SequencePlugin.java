package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class SequencePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		dispatch.schemaListener.getMaint().add((s, event) -> {
			s.addNode(appContext.getBean(SequenceGroup.class, s).load(event));
		});
	}
}
