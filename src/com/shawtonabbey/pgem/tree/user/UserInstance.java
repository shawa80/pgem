package com.shawtonabbey.pgem.tree.user;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbUser;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Instance;
import lombok.Getter;

@Component
@Scope("prototype")
public class UserInstance extends Instance<UserGroup>{
	@Getter
	private DbUser user;
			
	@Autowired
	EventDispatch dispatch;
		
	public interface Ev extends Add<UserInstance> {}
	
	public UserInstance(UserGroup parent, DbUser user)
	{
		super(parent, user.getName());
		this.user = user;
	}
	
	public UserInstance load(Event event) {

		event.lock(this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));			
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		
		return new ImageIcon(getClass().getResource("/images/table.png"));
	}

}
