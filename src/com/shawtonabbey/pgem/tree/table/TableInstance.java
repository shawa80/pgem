package com.shawtonabbey.pgem.tree.table;
import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class TableInstance extends DataGroup<TableGroup>
{	
	@Getter
	private DbTable table;
			
	public interface Added extends Add<TableInstance> {}
	
	public TableInstance(TableGroup parent, DbTable table)
	{
		super(parent, table.getName());
		this.table = table;
	}

	@Override
	protected SwingWorker<?> getWorker() {
		return null;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
	}
	

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}
	
}
