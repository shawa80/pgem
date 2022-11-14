package com.shawtonabbey.pgem.tree.index;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.database.index.DbIndex;
import com.shawtonabbey.pgem.database.index.DbIndexFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

import lombok.Getter;

@Component
@Scope("prototype")
public class IndexGroup extends XGroup<TableInstance> {

	
	@Getter
	private DbTable table;
	
	@Autowired
	private DbIndexFactory factory;
	
	public interface Added extends Add<IndexGroup> {}
		
	public IndexGroup(TableInstance parent, DbTable table)
	{
		super(parent, "Indexes");

		this.table = table;
	}


	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
	
	@Override
	protected SwingWorker<?> getWorker() {
		Event event = new Event();
		var sw = new SwingWorker<List<DbIndex>>()
				.setWork(() -> factory.getIndexes(findDbc(), table))
				.thenOnEdt((indexes) -> {
					indexes.stream()
						.map(x -> appContext.getBean(IndexInstance.class, this, x))
						.forEach(x -> {x.load(event); addNode(x);});
					
					doneLoading();	
				});
		return sw;
	}
	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}

}
