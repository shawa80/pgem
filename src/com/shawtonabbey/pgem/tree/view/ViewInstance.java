package com.shawtonabbey.pgem.tree.view;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbView;
import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import lombok.Getter;

@Component
@Scope("prototype")
public class ViewInstance extends Group<ViewGroup>
{
	@Getter
	private DbView view;

	@Autowired
	EventDispatch dispatch;
		
	public ViewInstance (ViewGroup parent, DbView view)
	{
		super(parent, view.getName());
		this.view = view;
	}
	
	public ViewInstance load(Event event) {

		event.lock(this);
		dispatch.viewListener.getDispatcher().added(this, event);
		event.unlock(this);
		
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/view.png"));
	}

	
}
