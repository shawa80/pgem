package com.shawtonabbey.pgem.tree.user;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.db.DbDatabase;
import com.shawtonabbey.pgem.database.user.DbUser;
import com.shawtonabbey.pgem.database.user.DbUserFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
@Scope("prototype")
public class UserGroup extends DataGroup<DatabaseInstance>
{
	private DbDatabase db;
	
	@Autowired
	private DbUserFactory factory;
	
	public interface Added extends Add<UserGroup> {}
	
	public UserGroup(DatabaseInstance parent, DbDatabase db)
	{
		super(parent, "Users");
		this.db = db;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<List<DbUser>>()
				.setWork(() -> factory.getUsers(findDbc(), db))
				.thenOnEdt((users) -> {
						
					users.stream()
						.map(x -> appContext.getBean(UserInstance.class, this, x))
						.forEach(x -> {x.load(event); addNode(x);});
					
					this.doneLoading();
				});
		return sw;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
	}

}
