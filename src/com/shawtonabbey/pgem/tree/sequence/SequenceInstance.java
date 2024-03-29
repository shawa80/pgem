package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.database.sequence.DbSequence;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class SequenceInstance extends Instance<SequenceGroup>
{	
	@Autowired
	EventDispatch dispatch;
	
	@Getter
	private DbSequence sequence;
	
	public interface Added extends Add<SequenceInstance> {}
	
	public SequenceInstance(SequenceGroup parent, DbSequence sequence)
	{
		super(parent, sequence.getName());
		
		this.sequence = sequence;
	}
	
	public SequenceInstance load(Event event) {
		
		dispatch.find(Added.class).fire(o->o.added(this, event));
				
		return this;
	}

}

