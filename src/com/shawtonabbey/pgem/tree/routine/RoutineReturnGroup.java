package com.shawtonabbey.pgem.tree.routine;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;
import com.shawtonabbey.pgem.tree.routine.RoutineParamGroup.Added;

import lombok.Getter;

@Component
@Scope("prototype")
public class RoutineReturnGroup extends XGroup<RoutineInstance>
{
	@Getter
	private RoutineInstance routine;

	public interface Added extends Add<RoutineReturnGroup> {}
	
	public RoutineReturnGroup(RoutineInstance routine)
	{
		super(routine, "Returns");
		
		this.routine = routine;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<String>()
				.setWork(() -> routine.routine.getReturnType(findDbc()))
				.thenOnEdt((returns) -> {
					
					var x = appContext.getBean(RoutineReturnInstance.class, this, returns);
					x.load(event); 
					addNode(x);

					doneLoading();
				});
		return sw;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
		
	}
	
}

