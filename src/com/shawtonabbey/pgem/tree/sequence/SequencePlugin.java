package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class SequencePlugin extends PluginBase {
	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(SequenceGroup.class, s).load(event));
		});
	}
}
