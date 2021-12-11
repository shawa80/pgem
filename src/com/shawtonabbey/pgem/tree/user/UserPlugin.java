package com.shawtonabbey.pgem.tree.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class UserPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		dispatch.databaseListener.getMaint().add((d, event) -> {
			d.addNode(appContext.getBean(UserGroup.class, d).load(event));
		});
	}
}