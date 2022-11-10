package com.shawtonabbey.pgem.tree.routine;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
public class RoutinePlugin  extends PluginBase {

	
	public void init() {
		dispatch.find(SchemaInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineGroup.class, s).load(event));
		});
		
		dispatch.find(RoutineInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineParamGroup.class, s).load(event));
		});
	
		dispatch.find(RoutineInstance.Added.class).listen((s, event) -> {
			s.addNode(appContext.getBean(RoutineReturnGroup.class, s).load(event));
		});
	}
}
