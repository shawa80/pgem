package com.shawtonabbey.pgem.tree.user;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.database.DbUser;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
@Scope("prototype")
public class UserGroup extends XGroup<DatabaseInstance>
{
	private DbDatabase db;
	
	public interface Ev extends Add<UserGroup> {}
	
	public UserGroup(DatabaseInstance parent, DbDatabase db)
	{
		super(parent, "Users");
		this.db = db;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorkerChain<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorkerChain<List<DbUser>>()
				.setWork(() -> DbUser.getUsers(db))
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
		dispatch.find(Ev.class).fire(o->o.added(this, event));
	}

}
