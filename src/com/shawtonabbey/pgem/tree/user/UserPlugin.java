package com.shawtonabbey.pgem.tree.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class UserPlugin implements Plugin {

	
	
	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(UserGroup.Ev.class);
		dispatch.register(UserInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(DatabaseInstance.Ev.class).listen((d, event) -> {
			d.addNode(appContext.getBean(UserGroup.class, d, d.getDatabase()).load(event));
		});
	}
}