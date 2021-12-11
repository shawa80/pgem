package com.shawtonabbey.pgem.tree.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class ColumnPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		dispatch.tableListener.getMaint().add((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getTable()).load(event));
		});
		
		dispatch.viewListener.getMaint().add((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getView()).load(event));
		});
	}
}
