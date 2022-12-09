package com.shawtonabbey.pgem.tree.table;
import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.database.table.DbTableFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class TableGroup extends DataGroup<SchemaInstance>
{
	@Autowired		
	private DbTableFactory factory;
	
	public interface Added extends Add<TableGroup> {}
	
	public TableGroup(SchemaInstance schema)
	{
		super(schema, "Tables");

	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this,  event));
	}
	
	@Override
	protected SwingWorker<List<DbTable>> getWorker() {
		
		Event tableLoad = new Event();
		var sw = new SwingWorker<List<DbTable>>()
			.setWork(() -> factory.getTables(findDbc(), getParentDb().getSchema()))
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
