package com.shawtonabbey.pgem.tree.view;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;
import com.shawtonabbey.pgem.tree.view.ViewGroup;

@Component
public class ViewPlugin extends PluginBase {
	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(ViewGroup.class, s).load(event));
		});
	}
}
