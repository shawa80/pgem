package com.shawtonabbey.pgem.tree.view;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.view.DbView;
import com.shawtonabbey.pgem.database.view.DbViewfactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class ViewGroup extends XGroup<SchemaInstance>
{
	private SchemaInstance schema;

	@Autowired
	private DbViewfactory factory;
	
	public interface Added extends Add<ViewGroup> {}

	
	public ViewGroup(SchemaInstance schema)
	{
		super(schema, "Views");
		this.schema = schema;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<List<DbView>>()
				.setWork(() -> factory.getViews(findDbc(), schema.getSchema()))
				.thenOnEdt((views) -> {
		
					views.stream()
						.map(x -> appContext.getBean(ViewInstance.class, this, x))
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
