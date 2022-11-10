package com.shawtonabbey.pgem.tree.rule;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;

@Component
@Scope("prototype")
public class RulePlugin  extends PluginBase {
	
	public void init() {
		dispatch.find(TableInstance.Added.class).listen((t, event) -> {
			t.addNode(appContext.getBean(RuleGroup.class, t, t.getTable()).load(event));
		});
		dispatch.find(ViewInstance.Added.class).listen((v, event) -> {
			v.addNode(appContext.getBean(RuleGroup.class, v, v.getView()).load(event));
		});
	}
}


