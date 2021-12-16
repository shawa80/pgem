package com.shawtonabbey.pgem.tree.table;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

import java.util.List;

import javax.swing.ImageIcon;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TableGroup extends XGroup<SchemaInstance>
{
			
	public interface Ev extends Add<TableGroup> {}
	
	public TableGroup(SchemaInstance schema)
	{
		super(schema, "Tables");

	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Ev.class).fire(o->o.added(this,  event));
	}
	
	@Override
	protected SwingWorkerChain<List<DbTable>> getWorker() {
		
		Event tableLoad = new Event();
		var sw = new SwingWorkerChain<List<DbTable>>()
			.setPrework(() -> {
				removeAll(); 
				setLoading();
			})
			.setWork(() -> DbTable.getTables(getParentDb().getSchema()))
			.thenOnEdt((tables) -> {
				tables.stream()
					.map(x -> appContext.getBean(TableInstance.class, this, x))
					.forEach(x -> {x.load(tableLoad); addNode(x);});
				
				doneLoading();
			})
			.setException((ex)->{
				setError();
			});

		return sw;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}


}
