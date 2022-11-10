package com.shawtonabbey.pgem.tree.user;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
public class UserPlugin  extends PluginBase {
	
	public void init() {
		dispatch.find(DatabaseInstance.Added.class).listen((d, event) -> {
			d.addNode(appContext.getBean(UserGroup.class, d, d.getDatabase()).load(event));
		});
	}
}