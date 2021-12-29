package com.shawtonabbey.pgem.tree.database;
import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class DatabaseInstance extends XGroup<ServerInstance>
{	
	@Getter
	private DbDatabase database;
	
	
	public interface Ev extends Add<DatabaseInstance> {}
	
	public DatabaseInstance(ServerInstance server, DbDatabase db)
	{
		super(server, db.getName());
		
		this.database = db;
	}
	
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/db.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		return null;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Ev.class).fire(o->o.added(this, event));
	}

	
}
