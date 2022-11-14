package com.shawtonabbey.pgem.tree.foreign;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.foreign.DbForeign;
import com.shawtonabbey.pgem.database.index.DbIndex;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class ForeignInstance extends Instance<ForeignGroup> {

	@Getter
	private DbForeign foreign;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<ForeignInstance> {}
	
	public ForeignInstance(ForeignGroup parent, DbForeign foreign)
	{
		super(parent, foreign.getName());
		this.foreign = foreign;
	}
	
	public ForeignInstance load(Event event) {

		event.lock(this);
		dispatch.find(Added.class).fire(o->o.added(this, event));				
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}