package com.shawtonabbey.pgem.tree.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class IndexPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(IndexGroup.Ev.class);
		dispatch.register(IndexInstance.Ev.class);
	}
	
	public void init() {
		dispatch.find(TableInstance.Ev.class).listen((t, event) -> {
			t.addNode(appContext.getBean(IndexGroup.class, t, t.getTable()).load(event));
		});
	}
}

