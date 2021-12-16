package com.shawtonabbey.pgem.tree.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class RoutinePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(RoutineGroup.Ev.class);
		dispatch.register(RoutineInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Ev.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineGroup.class, s).load(event));
		});
	}
}
