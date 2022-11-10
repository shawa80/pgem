package com.shawtonabbey.pgem.tree.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;

@Component
public class ColumnPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private ApplicationContext appContext;
	
	public void register() {
		dispatch.register(ColumnGroup.Added.class);
		dispatch.register(ColumnInstance.Ev.class);
	}
	
	public void init() {
		
		dispatch.find(TableInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getTable()).load(event));
		});
		
		dispatch.find(ViewInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getView()).load(event));
		});
	}
}
