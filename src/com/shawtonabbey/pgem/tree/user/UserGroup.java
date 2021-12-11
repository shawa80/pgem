package com.shawtonabbey.pgem.tree.user;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
@Scope("prototype")
public class UserGroup extends Group<DatabaseInstance>
{
	public UserGroup(DatabaseInstance db)
	{
		super(db, "Users");
	}
	
	public UserGroup load(Event event) {
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}
