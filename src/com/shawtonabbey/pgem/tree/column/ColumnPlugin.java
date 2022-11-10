package com.shawtonabbey.pgem.tree.column;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;

@Component
public class ColumnPlugin extends PluginBase {

	
	public void init() {
		
		dispatch.find(TableInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getTable()).load(event));
		});
		
		dispatch.find(ViewInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(ColumnGroup.class, t, t.getView()).load(event));
		});
	}
}
