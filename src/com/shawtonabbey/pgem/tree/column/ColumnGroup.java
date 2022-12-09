package com.shawtonabbey.pgem.tree.column;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbTableLike;
import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

@Component
@Scope("prototype")
public class ColumnGroup<T extends ItemModel> extends DataGroup<T> {

	@Autowired
	private DbColumnFactory factory;
	
	public interface Added extends Add<ColumnGroup<?>> {}
		
	private DbTableLike table;
	
	public ColumnGroup(T parent, DbTableLike table)
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
				return factory.getColumns(findDbc(), table);
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
