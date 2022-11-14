package com.shawtonabbey.pgem.tree.routine;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.routine.DbRoutine;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class RoutineInstance extends XGroup<RoutineGroup>
{	
	
	@Getter
	DbRoutine routine;
	
	public interface Added extends Add<RoutineInstance> {}
	
	public RoutineInstance(RoutineGroup parent, DbRoutine r)
	{
		super(parent, r.getName());
		this.routine = r;
	}
	

	@Override
	protected SwingWorker getWorker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}


}
