package com.shawtonabbey.pgem.tree.schema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.database.ConnectionInfo;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
public class SchemaPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(SchemaGroup.Ev.class);
		dispatch.register(SchemaInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(DatabaseInstance.Ev.class).listen((d, event) -> {
			
			boolean loadPgSchema = false;
			if (event.getParams().containsKey("ConnectionInfo") 
					&& event.getParams().get("ConnectionInfo") instanceof ConnectionInfo) {
				loadPgSchema = ((ConnectionInfo)event.getParams().get("ConnectionInfo")).isLoadPgSchema();
			}
					
			d.addNode(appContext.getBean(SchemaGroup.class, d, d.getDatabase()).load(loadPgSchema, event));
		});
	}
}