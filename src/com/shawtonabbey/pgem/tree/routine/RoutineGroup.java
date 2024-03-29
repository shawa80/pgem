package com.shawtonabbey.pgem.tree.routine;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.routine.DbRoutine;
import com.shawtonabbey.pgem.database.routine.DbRoutineFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class RoutineGroup extends DataGroup<SchemaInstance>
{
	private SchemaInstance schema;

	@Autowired
	private DbRoutineFactory factory;
	
	public interface Added extends Add<RoutineGroup> {}
	
	public RoutineGroup(SchemaInstance schema)
	{
		super(schema, "Functions");
		
		this.schema = schema;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<List<DbRoutine>>()
				.setWork(() -> factory.getRoutines(findDbc(), schema.getSchema()))
				.thenOnEdt((tables) -> {
					
					tables.stream()
							.map(x -> appContext.getBean(RoutineInstance.class, this, x))
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
