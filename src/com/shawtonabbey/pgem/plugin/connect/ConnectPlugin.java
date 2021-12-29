package com.shawtonabbey.pgem.plugin.connect;

import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.plugin.SysPlugin;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.DBManager;
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
	
	public void register() {
			
		javax.security.auth.login.Configuration.setConfiguration(new CustomConfig());
	
	}
	
	public void init() {
		
		Preferences pref;
		pref = Preferences.userRoot().node("com.shawtonabbey.pgem.tree.DBManager");
		
		dispatch.find(DatabaseInstance.Ev.class).listen((d, ev) -> {
			
			d.addPopup("Disconnect", (e)-> {
				d.findDbc().disconnect();
				
				d.parent.removeNode(d);
				
			});
			
		});
		
		dispatch.find(ServerInstance.Ev.class).listen((s, ev) -> {
			
			s.addPopup("Version", (e) -> {
				var db = (DatabaseInstance)s.getChildAt(0);
				
				window.launchQueryWin(db.findDbc(), "SELECT version();");
			});
			
		});
		
		dispatch.find(ServerInstance.Ev.class).listen((s, ev) -> {
			
			s.addPopup("Disconnect", (e) -> {
				int children = s.getChildCount();
				for (int i= 0; i< children; i++) {
					var d = (DatabaseInstance)s.getChildAt(i);
					d.findDbc().disconnect();
				}
				
				s.parent.removeNode(s);
				
			});
		});
		
		dispatch.find(DBManager.Ev.class).listen((m, ev) -> {
			
			m.addPopup("Connect", (e) -> {
				
				Event connectEvent = new Event();
				
				dispatch.find(SysPlugin.ConnectEv.class).fire(o->o.start());
				
				new SwingWorker<ServerInstance>()
					.setWork(() -> {
		
						connectEvent.whenFinished(() -> { 
							dispatch.find(SysPlugin.DisconnectEv.class).fire(o->o.end());
						});

						var connect = new ConnectDialog(window);
						connect.set(
								pref.get("address", ""), 
								pref.get("port", ""),
								pref.get("database", ""),
								pref.get("user", ""));
						
						var useFile = Boolean.parseBoolean(pref.get("krbUseFile", "false"));
						connect.setKerberos(
								pref.get("krbUser", ""),  
								pref.get("krbFile", ""), 
								useFile
								);
						connect.setTab(Integer.parseInt(pref.get("LoginTab", "0")));
						connect.setLocationRelativeTo(window);
						connect.setVisible(true);
						if (connect.selectedConnect())
						{
							pref.put("address", connect.getAddress());
							pref.put("port", connect.getPort());				
							pref.put("database", connect.getDatabase());
							pref.put("user", connect.getUser());
							
							pref.put("krbUser", connect.getKerberosUser());
							pref.put("krbFile", connect.getKerberosFile());
							pref.put("krbUseFile", connect.isKerberosfile()+"");
							
							pref.put("LoginTab",  connect.getTab()+"");
							
							UserPassPrompt.user = connect.getKerberosUser();
							UserPassPrompt.password = connect.getKerberosPass();
							
							KerberosLogin.cachePath = null;
							if (connect.isKerberosfile())
								KerberosLogin.cachePath = connect.getKerberosFile();
							
							var useKerberos = true;
							if (connect.getTab() == 0)
								useKerberos = false;
							
							var server = appContext.getBean(ServerInstance.class, m, window, connect, useKerberos);
									
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
