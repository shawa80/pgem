package com.shawtonabbey.pgem.tree.table;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class TablePlugin extends PluginBase {
	
	public void init() {
		
		dispatch.find(SchemaInstance.Added.class).listen((s, e) -> {
			s.addNode(appContext.getBean(TableGroup.class, s).load(e));
		});
	}
}
