package com.shawtonabbey.pgem.tree.column;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnCollection;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

@Component
@Scope("prototype")
public class ColumnGroup<T extends ItemModel> extends XGroup<T> {

	
	public interface Added extends Add<ColumnGroup<?>> {}
		
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
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<List<DbColumn>>()
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
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}

}
