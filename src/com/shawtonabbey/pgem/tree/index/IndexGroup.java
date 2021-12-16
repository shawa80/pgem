package com.shawtonabbey.pgem.tree.index;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbIndex;
import com.shawtonabbey.pgem.database.DbTable;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.table.TableInstance;

import lombok.Getter;

@Component
@Scope("prototype")
public class IndexGroup extends Group<TableInstance> {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	EventDispatch dispatch;
	
	@Getter
	private DbTable table;
	
	public interface Ev extends Add<IndexGroup> {}
		
	public IndexGroup(TableInstance parent, DbTable table)
	{
		super(parent, "Indexes");

		this.table = table;
	}
	public IndexGroup load(Event event) {

		event.lock(IndexGroup.this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		event.unlock(IndexGroup.this);
		
		var sw = new SwingWorkerChain<List<DbIndex>>()
			.setWork(() -> DbIndex.getIndexes(table))
			.thenOnEdt((indexes) -> {
				indexes.stream()
					.map(x -> appContext.getBean(IndexInstance.class, this, x))
					.forEach(x -> {x.load(event); addNode(x);});
				
				this.setName("Indexes");	

			});
		
		this.AddWillExpandListener(this, () -> {
			this.setName("Indexes (Loading)");
			sw.start();
		});
		
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}
