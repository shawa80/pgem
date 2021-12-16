package com.shawtonabbey.pgem.tree.column;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.ATreeNode;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.table.TableGroup;

@Component
@Scope("prototype")
public class ColumnGroup<T extends ATreeNode> extends Group<T> {

	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	EventDispatch dispatch;
	
	public interface Ev extends Add<ColumnGroup> {}
		
	private DbColumnCollection table;
	
	public ColumnGroup(T parent, DbColumnCollection table)
	{
		super(parent, "Columns");

		//this.parent = parent;
		this.table = table;
	}
	
	@SuppressWarnings("rawtypes")
	public ColumnGroup load(Event event) {
	
		event.lock(ColumnGroup.this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		event.unlock(ColumnGroup.this);
		
		var sw = new SwingWorkerChain<List<DbColumn>>()
		.setWork(() -> {
			return table.getColumns();
		})
		.thenOnEdt((columns) -> {
			
			columns.stream()
			.sorted((a, b) -> a.getName().compareTo(b.getName()))
			.forEach(c -> {
					var n = appContext.getBean(ColumnInstance.class, this, c);
					n.load(event);
					this.addNode(n);
			});

			this.setName("Columns");
		});
		this.AddWillExpandListener(this, () -> {
			this.setName("Columns (Loading)");
			sw.start();
		});
	

		
		return this;
	}
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}
