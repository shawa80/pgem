package com.shawtonabbey.pgem.tree.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class ColumnPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		dispatch.table.listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getTable()).load(event));
		});
		
		dispatch.view.listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getView()).load(event));
		});
	}
}
