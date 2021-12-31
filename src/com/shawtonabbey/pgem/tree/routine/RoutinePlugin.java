package com.shawtonabbey.pgem.tree.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class RoutinePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(RoutineGroup.Ev.class);
		dispatch.register(RoutineInstance.Ev.class);
		dispatch.register(RoutineParamGroup.Ev.class);
		dispatch.register(RoutineParamInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Ev.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineGroup.class, s).load(event));
		});
		
		dispatch.find(RoutineInstance.Ev.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineParamGroup.class, s).load(event));
		});
		
	}
}
