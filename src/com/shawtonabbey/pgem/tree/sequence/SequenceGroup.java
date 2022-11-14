package com.shawtonabbey.pgem.tree.sequence;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.sequence.DbSequence;
import com.shawtonabbey.pgem.database.sequence.DbSequenceFactory;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class SequenceGroup extends XGroup<SchemaInstance>
{	
	@Autowired
	private DbSequenceFactory sequenceFactory;
	
	
	public interface Added extends Add<SequenceGroup> {}
	
	public SequenceGroup(SchemaInstance schema)
	{
		super(schema, "Sequence");
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		Event event = new Event();
		var sw = new SwingWorker<List<DbSequence>>()
				.setWork(() -> sequenceFactory.getSequence(findDbc(), getParentDb().getSchema()))
				.thenOnEdt((seq) -> {

					seq.stream()
							.map(x -> appContext.getBean(SequenceInstance.class, this, x))
							.forEach(x-> {x.load(event); addNode(x);});
				
					doneLoading();
				});
		return sw;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
	}

}

