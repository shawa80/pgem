package com.shawtonabbey.pgem.tree.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbSequence;
import com.shawtonabbey.pgem.plugin.EventDispatch;
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
	
	public SequenceInstance(SequenceGroup parent, DbSequence sequence)
	{
		super(parent, sequence.getName());
		
		this.sequence = sequence;
	}
	
	public SequenceInstance load(Event event) {
		
		dispatch.sequenceListener.getDispatcher().added(this, event);
				
		return this;
	}

}

