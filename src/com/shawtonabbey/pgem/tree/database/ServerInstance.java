package com.shawtonabbey.pgem.tree.database;

import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.tree.DBManager;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.ui.MainWindow;
import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.database.DbServer;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.connect.ConnectDialog;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;

@Component
@Scope("prototype")
public class ServerInstance extends Group<DBManager>
{
	@Autowired
	private ApplicationContext appContext;

	private ConnectDialog params;
	@Autowired
	EventDispatch dispatch;
	
	@Autowired
	private MainWindow win;

	
	private String name;
	
	public ServerInstance(DBManager dbManager, PgemMainWindow window, ConnectDialog params)
	{
		super(dbManager, params.getAddress());

		name = params.getAddress();
		
		this.params = params;
	}
	
	public void reload(Event event) {
		this.removeAll();
		try {
			this.load(event);
		} catch (IOException e) {
		}
	}
	
	public void load(Event event) throws IOException {
		
		dispatch.server.fire(o->o.added(this,event));
		event.lock(ServerInstance.this);
		this.setName(name + " (Loading)");
		event.whenFinished(() -> {this.setName(name);});
		
		var db = params.getDatabase().trim();
		final var loadAll = "".equals(db) ? true : false;
				
		var server = new DbServer(params.getAddress());
		
		var eventInfo = new ConnectionInfo();
		eventInfo.setLoadPgSchema(params.getUsePgSchema());
		
		new SwingWorkerChain<List<DbDatabase>>()
		.setWork(() -> {

			try (var conn = DBC.connect(params.getAddress(), params.getPort(),
					db, params.getUser(), params.getPass()))
			{
				var dbs = DbServer.getDatabases(conn, server);
				return dbs;
			}
		})
		.thenOnEdt((dbs) -> {
			final var selectedDb = db;
			dbs.stream()
			   .filter((x) -> x.getName().equals(selectedDb) || loadAll)
			   .forEach((x) -> {
	
					var dbi = appContext.getBean(DatabaseInstance.class, this, x);
					addNode(dbi);
					event.getParams().put("ConnectionInfo", eventInfo);
					dbi.load(event);
			});				
		
			event.unlock(ServerInstance.this);
		})
		.setException((ex) -> {
			event.unlock(ServerInstance.this);
			win.message(ex.getMessage());
			this.setName(name + " (Error)");
		})
		.start();
							
		
	}
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/server.png"));
	}
}