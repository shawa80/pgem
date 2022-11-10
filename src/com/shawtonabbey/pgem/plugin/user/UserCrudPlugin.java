package com.shawtonabbey.pgem.plugin.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.tree.user.UserGroup;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class UserCrudPlugin extends PluginBase {
	
	@Autowired
	private MainWindow win;
	

	@Override
	public void init() {
		dispatch.find(UserGroup.Added.class).listen((users, ev) -> {
			users.addPopup("Add", (e) -> {
				win.launchQueryWin(users.findDbc(), 
						"CREATE USER name");
			});
		});
	}

}
