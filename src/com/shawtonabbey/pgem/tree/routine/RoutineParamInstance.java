package com.shawtonabbey.pgem.tree.routine;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbIndex;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import com.shawtonabbey.pgem.tree.index.IndexGroup;
import com.shawtonabbey.pgem.tree.index.IndexInstance;
import com.shawtonabbey.pgem.tree.index.IndexInstance.Ev;

import lombok.Getter;

@Component
@Scope("prototype")
public class RoutineParamInstance extends Instance<RoutineParamGroup> {

	@Getter
	private String type;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Ev extends Add<RoutineParamInstance> {}
	
	public RoutineParamInstance(RoutineParamGroup parent, String type)
	{
		super(parent, type);
		this.type = type;
	}
	
	public RoutineParamInstance load(Event event) {

		event.lock(this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));				
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}
