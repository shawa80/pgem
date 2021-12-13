package com.shawtonabbey.pgem.plugin.connect;

import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.database.ServerInstance;

@Component
public class ConnectPlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	@Autowired
	private PgemMainWindow window;
	@Autowired
	private ApplicationContext appContext;
	
	public void init() {
		
		Preferences pref;
		pref = Preferences.userRoot().node("com.shawtonabbey.pgem.tree.DBManager");
		
		dispatch.database.listen((d, ev) -> {
			
			d.addPopup("Disconnect", (e)-> {
				d.getDatabase().getDbInstance().disconnect();
				
				d.parent.removeNode(d);
				
			});
			
		});
		
		dispatch.server.listen((s, ev) -> {
			
			s.addPopup("Version", (e) -> {
				var db = (DatabaseInstance)s.getChildAt(0);
				
				window.launchQueryWin(db.getDatabase().getDbInstance(), "SELECT version();");
			});
			
		});
		
		dispatch.server.listen((s, ev) -> {
			
			s.addPopup("Disconnect", (e) -> {
				int children = s.getChildCount();
				for (int i= 0; i< children; i++) {
					var d = (DatabaseInstance)s.getChildAt(i);
					d.getDatabase().getDbInstance().disconnect();
				}
				
				s.parent.removeNode(s);
				
			});
		});
		
		dispatch.dbManager.listen((m, ev) -> {
			
			m.addPopup("Connect", (e) -> {
				
				Event connectEvent = new Event();
				
				dispatch.connectStart.fire(o->o.start());
				
				new SwingWorkerChain<ServerInstance>()
					.setWork(() -> {
		
						connectEvent.whenFinished(() -> { dispatch.connectEnd.fire(o->o.end());});

						var connect = new ConnectDialog(window);
						connect.set(
								pref.get("address", ""), 
								pref.get("port", ""),
								pref.get("database", ""),
								pref.get("user", ""));
						connect.setLocationRelativeTo(window);
						connect.setVisible(true);
						if (connect.selectedConnect())
						{
							pref.put("address", connect.getAddress());
							pref.put("port", connect.getPort());				
							pref.put("database", connect.getDatabase());
							pref.put("user", connect.getUser());
							
							var server = appContext.getBean(ServerInstance.class, m, window, connect);
									
							return server;
						}
						
						return null;
					})
					.thenOnEdt((server) -> {
						server.load(connectEvent);
						m.addNode(server);
					})
					.setException(ex -> {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					})
					.start();
				
			});
		});
		
	}
	
}