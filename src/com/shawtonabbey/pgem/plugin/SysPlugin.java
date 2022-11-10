package com.shawtonabbey.pgem.plugin;

import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.event.Open;
import com.shawtonabbey.pgem.event.Save;
import com.shawtonabbey.pgem.event.Start;
import com.shawtonabbey.pgem.event.Stop;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.tree.DBManager;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.database.ServerInstance;

@Component
public class SysPlugin implements Plugin {

	public interface MenuEv extends Add<JMenuBar> {}
	public interface SaveEv extends Save {}
	public interface OpenEv extends Open {}
	public interface ConnectEv extends Start {}
	public interface DisconnectEv extends Stop {}
	
	@Autowired
	private EventDispatch dispatch;
	
	public void register() {
		dispatch.register(DatabaseInstance.Added.class);
		dispatch.register(ServerInstance.Added.class);
		dispatch.register(DBManager.Ev.class);
		dispatch.register(AQueryWindow.Ev.class);
		dispatch.register(MenuEv.class);
		
		dispatch.register(SaveEv.class);
		dispatch.register(OpenEv.class);
		dispatch.register(ConnectEv.class);
		dispatch.register(DisconnectEv.class);
	}
	
	public void init() {
		
	}
}
