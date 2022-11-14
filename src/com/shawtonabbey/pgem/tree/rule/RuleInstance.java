package com.shawtonabbey.pgem.tree.rule;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.database.rule.DbRule;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class RuleInstance extends Instance<RuleGroup> {

	@Getter
	private DbRule rule;
		
	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<RuleInstance> {}
	
	public RuleInstance(RuleGroup parent, DbRule rule)
	{
		super(parent, rule.getName());
		this.rule = rule;
	}
	
	public RuleInstance load(Event event) {

		event.lock(this);
		dispatch.find(Added.class).fire(o->o.added(this, event));				
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

	
}

