package com.shawtonabbey.pgem.tree.routine;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbRoutine;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class RoutineGroup extends Group<SchemaInstance>
{
	private SchemaInstance schema;
	@Autowired
	private ApplicationContext appContext;

	@Autowired
	private EventDispatch dispatch;

	
	public RoutineGroup(SchemaInstance schema)
	{
		super(schema, "Functions");
		
		this.schema = schema;
	}
	
	public RoutineGroup load(Event event) {
		
		populate(event);
		
		return this;
	}
	
	private void populate(Event event)
	{
		event.lock(RoutineGroup.this);
		dispatch.routineGroup.getDispatcher().added(this, event);
		event.unlock(RoutineGroup.this);
		
		var sw = new SwingWorkerChain<List<DbRoutine>>()
			.setWork(() -> DbRoutine.getRoutines(schema.getSchema()))
			.thenOnEdt((tables) -> {
				
				tables.stream()
						.map(x -> appContext.getBean(RoutineInstance.class, this, x))
						.forEach(x -> {x.load(event); addNode(x);});

				this.setName("Functions");
			});

		this.AddWillExpandListener(this, () -> {
			this.setName("Functions (Loading)");
			sw.start();
		});

		
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
	
}
