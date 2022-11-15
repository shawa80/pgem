package com.shawtonabbey.pgem.plugin.connect;

import java.util.prefs.Preferences;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.kerberos.kcm.KerberosCacheIntercept;
import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.SysPlugin;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.DBManager;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.database.ServerInstance;

@Component
public class ConnectPlugin extends PluginBase {

	
	@Autowired
	private PgemMainWindow window;

	
	public void register() {
			
		javax.security.auth.login.Configuration.setConfiguration(new CustomConfig());
		KerberosCacheIntercept.installKcm();
	}
	
	public void init() {
		
		Preferences pref;
		pref = Preferences.userRoot().node("com.shawtonabbey.pgem.tree.DBManager");
		
		dispatch.find(DatabaseInstance.Added.class).listen((d, ev) -> {
			
			d.addPopup("Disconnect", (e)-> {
				d.findDbc().disconnect();
				
				d.parent.removeNode(d);
				
			});
			
		});
		
		dispatch.find(ServerInstance.Added.class).listen((s, ev) -> {
			
			s.addPopup("Version", (e) -> {
				var db = (DatabaseInstance)s.getChildAt(0);
				
				window.launchQueryWin(db.findDbc(), "SELECT version();");
			});
			
		});
		
		dispatch.find(ServerInstance.Added.class).listen((s, ev) -> {
			
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
				connect.setLoadDbs(Boolean.parseBoolean(pref.get("LoadDbs", "false")));
				connect.setTab(Integer.parseInt(pref.get("LoginTab", "0")));
				connect.setLocationRelativeTo(window);
				connect.setVisible(true);
				
				new SwingWorker<ServerInstance>()
					.setWork(() -> {
						
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
							
							pref.put("LoadDbs", connect.getLoadDbs()+"");
							
							UserPassPrompt.user = connect.getKerberosUser();
							UserPassPrompt.password = connect.getKerberosPass();
							
							KerberosLogin.cachePath = null;
							if (connect.isKerberosfile())
								KerberosLogin.cachePath = connect.getKerberosFile();
							
							var useKerberos = true;
							if (connect.getTab() == 0)
								useKerberos = false;

							var loadDbs = connect.getLoadDbs();
							
							var server = appContext.getBean(ServerInstance.class, m, window, connect, useKerberos, loadDbs);
									
							return server;
						}	
						return null;
					})
					.thenOnEdt((server) -> {
						if (server == null)
							return;
						
						m.addNode(server);
						server.load(connectEvent);
						
					})
					.setException(ex -> {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					})
					.start();
				
			});
		});
		
	}
	
}
