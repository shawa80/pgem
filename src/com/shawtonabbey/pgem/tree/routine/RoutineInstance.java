package com.shawtonabbey.pgem.tree.routine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbRoutine;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;

import lombok.Getter;

@Component
@Scope("prototype")
public class RoutineInstance extends Instance<RoutineGroup>
{	
	@Autowired
	EventDispatch dispatch;
	
	@Getter
	DbRoutine routine;
	
	public RoutineInstance(RoutineGroup parent, DbRoutine r)
	{
		super(parent, r.getName());
		this.routine = r;
	}
	
	public RoutineInstance load(Event event) {
		
		dispatch.routine.fire(o->o.added(this, event));
		
		return this;
	}


}
