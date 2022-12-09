package com.shawtonabbey.pgem.tree.trigger;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.trigger.DbTrigger;
import com.shawtonabbey.pgem.database.trigger.DbTriggerFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
@Scope("prototype")
public class TriggerGroup extends DataGroup<TableInstance>
{
			
	@Autowired
	private DbTriggerFactory factory;
	
	public interface Added extends Add<TriggerGroup> {}
	
	public TriggerGroup(TableInstance table)
	{
		super(table, "Trigger");

	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this,  event));
	}
	
	@Override
	protected SwingWorker<List<DbTrigger>> getWorker() {
		
		Event tableLoad = new Event();
		var sw = new SwingWorker<List<DbTrigger>>()
			.setWork(() -> factory.getTriggers(findDbc(), getParentDb().getTable()))
			.thenOnEdt((triggers) -> {
				triggers.stream()
					.map(x -> appContext.getBean(TriggerInstance.class, this, x))
					.forEach(x -> {x.load(tableLoad); addNode(x);});
				
				doneLoading();
			});
		return sw;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}


}

