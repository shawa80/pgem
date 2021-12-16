package com.shawtonabbey.pgem.tree.database;
import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.column.ColumnGroup.Ev;
import com.shawtonabbey.pgem.tree.table.TableGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class DatabaseInstance extends Group<ServerInstance>
{	
	@Getter
	private DbDatabase database;
	
	@Autowired
	EventDispatch dispatch;
	
	public interface Ev extends Add<DatabaseInstance> {}
	
	public DatabaseInstance(ServerInstance server, DbDatabase db)
	{
		super(server, db.getName());
		
		this.database = db;
	}
	
	public void load(Event event) {
		
		dispatch.find(Ev.class).fire(o->o.added(this, event));
			
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/db.png"));
	}

	
}
