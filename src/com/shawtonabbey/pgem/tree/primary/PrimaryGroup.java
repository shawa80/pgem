package com.shawtonabbey.pgem.tree.primary;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.primary.DbPrimaryFactory;
import com.shawtonabbey.pgem.database.primary.DbPrimaryKey;
import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.table.TableInstance;

import lombok.Getter;

@Component
@Scope("prototype")
public class PrimaryGroup extends DataGroup<TableInstance> {

	@Getter
	private DbTable table;
	
	@Autowired
	private DbPrimaryFactory factory;
	
	public interface Added extends Add<PrimaryGroup> {}
		
	public PrimaryGroup(TableInstance parent, DbTable table)
	{
		super(parent, "Primary");

		this.table = table;
	}


	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
	
	@Override
	protected SwingWorker<?> getWorker() {
		Event event = new Event();
		var sw = new SwingWorker<DbPrimaryKey>()
				.setWork(() -> factory.getPrimary(findDbc(), table))
				.thenOnEdt((key) -> {
					
					var instance = appContext.getBean(PrimaryInstance.class, this, key);
					addNode(instance);					
					doneLoading();	
				});
		return sw;
	}
	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}
}
