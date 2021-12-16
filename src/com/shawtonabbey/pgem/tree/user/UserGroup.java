package com.shawtonabbey.pgem.tree.user;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.database.DbUser;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.column.ColumnGroup.Ev;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
@Scope("prototype")
public class UserGroup extends Group<DatabaseInstance>
{
	private DbDatabase db;
	@Autowired
	private ApplicationContext appContext;
	@Autowired
	private EventDispatch dispatch;
	
	public interface Ev extends Add<UserGroup> {}
	
	public UserGroup(DatabaseInstance parent, DbDatabase db)
	{
		super(parent, "Users");
		this.db = db;
	}
	
	public UserGroup load(Event event) {
		
		event.lock(UserGroup.this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		event.unlock(UserGroup.this);
		
		var sw = new SwingWorkerChain<List<DbUser>>()
			.setWork(() -> DbUser.getUsers(db))
			.thenOnEdt((users) -> {
					
				users.stream()
					.map(x -> appContext.getBean(UserInstance.class, this, x))
					.forEach(x -> {x.load(event); addNode(x);});
				
				this.doneLoading();
			});
		
		this.AddWillExpandListener(this, () -> {
			this.setLoading();
			sw.start();
		});

		
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}
