package com.shawtonabbey.pgem.tree.schema;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbDatabase;
import com.shawtonabbey.pgem.database.DbSchema;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.database.ConnectionInfo;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;

@Component
@Scope("prototype")
public class SchemaGroup extends Group<DatabaseInstance>
{	
	private DbDatabase db;
	
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private EventDispatch dispatch;

	public interface Ev extends Add<SchemaGroup> {}
	
	public SchemaGroup(DatabaseInstance parent, DbDatabase db)
	{
		super(parent, "Schemas");

		this.db = db;
	}
	
	public SchemaGroup load(Event event) {

		
		boolean loadPgSchema;
		if (event.getParams().containsKey("ConnectionInfo") 
				&& event.getParams().get("ConnectionInfo") instanceof ConnectionInfo) {
			loadPgSchema = ((ConnectionInfo)event.getParams().get("ConnectionInfo")).isLoadPgSchema();
		} else {
			loadPgSchema = false;
		}
		
		event.lock(SchemaGroup.this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		event.unlock(SchemaGroup.this);
		
		var sw = new SwingWorker<List<DbSchema>>()
		.setWork(() -> DbSchema.getSchemas(db, loadPgSchema))
		.thenOnEdt((schemas) -> {
			
			schemas.stream()
					.map(x -> appContext.getBean(SchemaInstance.class, this, x))
					.forEach(x -> {addNode(x); x.load(event);});
			
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
