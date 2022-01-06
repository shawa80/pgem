package com.shawtonabbey.pgem.tree.trigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTrigger;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class TriggerInstance extends Instance<TriggerGroup>
{	
	@Autowired
	EventDispatch dispatch;
	
	@Getter
	private DbTrigger trigger;
	
	public interface Ev extends Add<TriggerInstance> {}
	
	public TriggerInstance(TriggerGroup parent, DbTrigger trigger)
	{
		super(parent, trigger.getName());
		
		this.trigger = trigger;
	}
	
	public TriggerInstance load(Event event) {
		
		dispatch.find(Ev.class).fire(o->o.added(this, event));
				
		return this;
	}

}


