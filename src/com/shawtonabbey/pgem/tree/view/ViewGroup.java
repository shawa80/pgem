package com.shawtonabbey.pgem.tree.view;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbView;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class ViewGroup extends Group<SchemaInstance>
{
	@Autowired
	private ApplicationContext appContext;

	private SchemaInstance schema;

	@Autowired
	private EventDispatch dispatch;

	
	public ViewGroup(SchemaInstance schema)
	{
		super(schema, "Views");
		this.schema = schema;
	}
	
	public ViewGroup load(Event event) {
		
		event.lock(ViewGroup.this);
		dispatch.viewGroup.getDispatcher().added(this, event);
		event.unlock(ViewGroup.this);
		
		var sw = new SwingWorkerChain<List<DbView>>()
			.setWork(() -> DbView.getViews(schema.getSchema()))
			.thenOnEdt((views) -> {
	
				views.stream()
					.map(x -> appContext.getBean(ViewInstance.class, this, x))
					.forEach(x -> {x.load(event); addNode(x);});
				this.setName("Views");
			});
		
		this.AddWillExpandListener(this, () -> {
			this.setName("Views (Loading)");
			sw.start();
		});

		
		return this;
	}

	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}
