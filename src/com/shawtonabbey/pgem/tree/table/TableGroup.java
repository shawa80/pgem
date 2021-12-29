package com.shawtonabbey.pgem.tree.table;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
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
	protected SwingWorker<List<DbTable>> getWorker() {
		
		Event tableLoad = new Event();
		var sw = new SwingWorker<List<DbTable>>()
			.setWork(() -> DbTable.getTables(getParentDb().getSchema()))
			.thenOnEdt((tables) -> {
				tables.stream()
					.map(x -> appContext.getBean(TableInstance.class, this, x))
					.forEach(x -> {x.load(tableLoad); addNode(x);});
				
				doneLoading();
			});
		return sw;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}


}
