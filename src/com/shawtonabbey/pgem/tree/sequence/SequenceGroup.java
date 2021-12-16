package com.shawtonabbey.pgem.tree.sequence;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbSequence;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;
import com.shawtonabbey.pgem.tree.column.ColumnGroup.Ev;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class SequenceGroup extends Group<SchemaInstance>
{	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private EventDispatch dispatch;
	
	public interface Ev extends Add<SequenceGroup> {}
	
	public SequenceGroup(SchemaInstance schema)
	{
		super(schema, "Sequence");
	}
	
	public SequenceGroup load(Event event) {

		event.lock(SequenceGroup.this);
		dispatch.find(Ev.class).fire(o->o.added(this, event));
		event.unlock(SequenceGroup.this);
		
		var sw = new SwingWorkerChain<List<DbSequence>>()
		.setWork(() -> DbSequence.getSequence(getParentDb().getSchema()))
		.thenOnEdt((seq) -> {

			seq.stream()
					.map(x -> appContext.getBean(SequenceInstance.class, this, x))
					.forEach(x-> {x.load(event); addNode(x);});
		
			this.setName("Sequence");
		});
		
		this.AddWillExpandListener(this, () -> {
			this.setName("Sequence (Loading)");
			sw.start();
		});

		
		return this;
		
	}
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

}

