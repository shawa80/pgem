package com.shawtonabbey.pgem.tree.primary;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class PrimaryPlugin extends PluginBase {

	public void init() {
		
		dispatch.find(TableInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(PrimaryGroup.class, t, t.getTable()).load(event));
		});
	}
}
