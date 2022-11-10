package com.shawtonabbey.pgem.tree.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@SuppressWarnings("rawtypes")
@Component
@Scope("prototype")
public class ColumnInstance extends Instance<ColumnGroup> {

	
	@Getter
	private DbColumn column;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<ColumnInstance> {}
	
	public ColumnInstance(ColumnGroup parent, DbColumn col) {
		super(parent, col.getName() + " (" + col.getType() + ")");
		
		this.column = col;
	}
	
	public ColumnInstance load(Event event) {

		dispatch.find(Added.class).fire(o->o.added(this, event));
		
		return this;
	}
	
}
