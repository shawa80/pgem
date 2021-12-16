package com.shawtonabbey.pgem.tree.index;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbIndex;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import com.shawtonabbey.pgem.tree.column.ColumnGroup.Ev;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

import lombok.Getter;

@Component
@Scope("prototype")
public class IndexInstance extends Instance<IndexGroup> {

	@Getter
	private DbIndex index;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Ev extends Add<IndexInstance> {}
	
	public IndexInstance(IndexGroup parent, DbIndex index)
	{
		super(parent, index.getName());
		this.index = index;
	}
	
	public IndexInstance load(Event event) {

		event.lock(this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));				
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}
