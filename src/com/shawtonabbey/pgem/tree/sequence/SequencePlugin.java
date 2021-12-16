package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class SequencePlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(SequenceGroup.Ev.class);
		dispatch.register(SequenceInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(SchemaInstance.Ev.class).listen((s, event) -> {
			s.addNode(appContext.getBean(SequenceGroup.class, s).load(event));
		});
	}
}
