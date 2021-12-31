package com.shawtonabbey.pgem.plugin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.user.UserGroup;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class UserCrudPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	private MainWindow win;
	
	@Override
	public void register() {
			
	}

	@Override
	public void init() {
		dispatch.find(UserGroup.Ev.class).listen((users, ev) -> {
			users.addPopup("Add", (e) -> {
				win.launchQueryWin(users.findDbc(), 
						"CREATE USER name");
			});
		});
	}

}
