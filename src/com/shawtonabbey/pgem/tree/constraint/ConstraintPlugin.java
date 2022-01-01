package com.shawtonabbey.pgem.tree.constraint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.index.IndexGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class ConstraintPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(ConstraintGroup.Ev.class);
		dispatch.register(ConstraintInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(TableInstance.Ev.class).listen((t, event) -> {
			t.addNode(appContext.getBean(ConstraintGroup.class, t, t.getTable()).load(event));
		});
	}
}


