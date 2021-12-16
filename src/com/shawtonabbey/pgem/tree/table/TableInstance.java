package com.shawtonabbey.pgem.tree.table;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import lombok.Getter;

@Component
@Scope("prototype")
public class TableInstance extends Group<TableGroup>
{	
	@Getter
	private DbTable table;
			
	@Autowired
	EventDispatch dispatch;
		
	public interface Ev extends Add<TableInstance> {}
	
	public TableInstance(TableGroup parent, DbTable table)
	{
		super(parent, table.getName());
		this.table = table;
	}
	
	public TableInstance load(Event event) {

		event.lock(this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));			
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}
