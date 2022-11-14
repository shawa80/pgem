package com.shawtonabbey.pgem.tree.constraint;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.database.constraint.DbConstraint;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class ConstraintInstance extends Instance<ConstraintGroup> {

	@Getter
	private DbConstraint con;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<ConstraintInstance> {}
	
	public ConstraintInstance(ConstraintGroup parent, DbConstraint con)
	{
		super(parent, con.getName());
		this.con = con;
	}
	
	public ConstraintInstance load(Event event) {

		event.lock(this);
		dispatch.find(Added.class).fire(o->o.added(this, event));				
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}
