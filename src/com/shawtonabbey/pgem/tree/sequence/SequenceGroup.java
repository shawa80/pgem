package com.shawtonabbey.pgem.tree.sequence;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbSequence;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;

@Component
@Scope("prototype")
public class SequenceGroup extends XGroup<SchemaInstance>
{	
	
	public interface Ev extends Add<SequenceGroup> {}
	
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
				.setWork(() -> DbSequence.getSequence(findDbc(), getParentDb().getSchema()))
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
		dispatch.find(Ev.class).fire(o->o.added(this, event));
	}

}

