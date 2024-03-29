package com.shawtonabbey.pgem.tree.rule;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.rule.DbRule;
import com.shawtonabbey.pgem.database.rule.DbRuleFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

import lombok.Getter;

@Component
@Scope("prototype")
public class RuleGroup extends DataGroup<ItemModel> {

	
	@Getter
	private DbTableLike table;
	
	@Autowired
	private DbRuleFactory factory;
	
	public interface Added extends Add<RuleGroup> {}
		
	public RuleGroup(ItemModel parent, DbTableLike table)
	{
		super(parent, "Rules");

		this.table = table;
	}


	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
	
	@Override
	protected SwingWorker<?> getWorker() {
		Event event = new Event();
		var sw = new SwingWorker<List<DbRule>>()
				.setWork(() -> factory.getRules(findDbc(), table))
				.thenOnEdt((rules) -> {
					rules.stream()
						.map(x -> appContext.getBean(RuleInstance.class, this, x))
						.forEach(x -> {x.load(event); addNode(x);});
					
					doneLoading();	
				});
		return sw;
	}
	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}

}
