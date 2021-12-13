package com.shawtonabbey.pgem.tree.table;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.debug.DebugWindow;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

import java.util.List;

import javax.swing.ImageIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TableGroup extends Group<SchemaInstance>
{
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	EventDispatch dispatch;
			
	@Autowired
	private DebugWindow win;
	
	public TableGroup(SchemaInstance schema)
	{
		super(schema, "Tables");

	}
	public TableGroup load(Event event) {

		event.lock(TableGroup.this);
		dispatch.tableGroup.fire(o->o.added(this, event));
		event.unlock(TableGroup.this);	
		
		Event tableLoad = new Event();
		var sw = new SwingWorkerChain<List<DbTable>>()
		.setWork(() -> DbTable.getTables(getParentDb().getSchema()))
		.thenOnEdt((tables) -> {
			tables.stream()
				.map(x -> appContext.getBean(TableInstance.class, this, x))
				.forEach(x -> {x.load(tableLoad); addNode(x);});
			
			this.setName("Tables");
		})
		.setException((ex)->{
			this.setName("Tables (Error)");
		});
		
		this.AddWillExpandListener(this, () -> {
			win.setMessage("expanding");
			this.setName("Tables (Loading)");
			sw.start();
		});
				
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
}
