package com.shawtonabbey.pgem.tree.constraint;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.constraint.DbConstraint;
import com.shawtonabbey.pgem.database.constraint.DbConstraintFactory;
import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;

import lombok.Getter;


@Component
@Scope("prototype")
public class ConstraintGroup extends DataGroup<TableInstance> {

	
	@Autowired
	private DbConstraintFactory constFactory;
	
	@Getter
	private DbTable table;
	
	public interface Added extends Add<ConstraintGroup> {}
		
	public ConstraintGroup(TableInstance parent, DbTable table)
	{
		super(parent, "Constraints");

		this.table = table;
	}


	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
	
	@Override
	protected SwingWorker<?> getWorker() {
		Event event = new Event();
		var sw = new SwingWorker<List<DbConstraint>>()
				.setWork(() -> constFactory.getConstraints(findDbc(), table))
				.thenOnEdt((indexes) -> {
					indexes.stream()
						.map(x -> appContext.getBean(ConstraintInstance.class, this, x))
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

