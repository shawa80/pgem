package com.shawtonabbey.pgem.tree.routine;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbRoutine;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
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
	
	public interface Ev extends Add<RoutineInstance> {}
	
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
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		
	}


}
