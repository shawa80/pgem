package com.shawtonabbey.pgem.tree.rule;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbRule;
import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

import lombok.Getter;

@Component
@Scope("prototype")
public class RuleGroup extends XGroup<ItemModel> {

	
	@Getter
	private DbTableLike table;
	
	public interface Ev extends Add<RuleGroup> {}
		
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
				.setWork(() -> DbRule.getRules(findDbc(), table))
				.thenOnEdt((indexes) -> {
					indexes.stream()
						.map(x -> appContext.getBean(RuleInstance.class, this, x))
						.forEach(x -> {x.load(event); addNode(x);});
					
					doneLoading();	
				});
		return sw;
	}
	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		
	}

}
