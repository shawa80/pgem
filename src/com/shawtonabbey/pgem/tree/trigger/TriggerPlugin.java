package com.shawtonabbey.pgem.tree.trigger;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class TriggerPlugin extends PluginBase {
	
	public void init() {
		dispatch.find(TableInstance.Added.class).listen((s, e) -> {
			s.addNode(appContext.getBean(TriggerGroup.class, s).load(e));
		});
	}
}

