package com.shawtonabbey.pgem.tree.view;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbView;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class ViewGroup extends XGroup<SchemaInstance>
{
	private SchemaInstance schema;

	
	public interface Ev extends Add<ViewGroup> {}

	
	public ViewGroup(SchemaInstance schema)
	{
		super(schema, "Views");
		this.schema = schema;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorkerChain<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorkerChain<List<DbView>>()
				.setWork(() -> DbView.getViews(schema.getSchema()))
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
		dispatch.find(Ev.class).fire(o->o.added(this, event));
	}

}
