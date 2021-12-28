package com.shawtonabbey.pgem.tree.column;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.database.DbColumnCollection;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.ATreeNode;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;

@Component
@Scope("prototype")
public class ColumnGroup<T extends ATreeNode> extends XGroup<T> {

	
	public interface Ev extends Add<ColumnGroup<?>> {}
		
	private DbColumnCollection table;
	
	public ColumnGroup(T parent, DbColumnCollection table)
	{
		super(parent, "Columns");

		this.table = table;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorkerChain<?> getWorker() {
		
		Event event = new Event();
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

				doneLoading();
			});
		return sw;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		
	}

}
