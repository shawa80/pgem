package com.shawtonabbey.pgem.tree.routine;

import java.util.List;

import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class RoutineParamGroup extends DataGroup<RoutineInstance>
{
	@Getter
	private RoutineInstance routine;

	public interface Added extends Add<RoutineParamGroup> {}
	
	public RoutineParamGroup(RoutineInstance routine)
	{
		super(routine, "Params");
		
		this.routine = routine;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		
		Event event = new Event();
		var sw = new SwingWorker<List<String>>()
				.setWork(() -> routine.routine.getRoutinesParams(findDbc()))
				.thenOnEdt((params) -> {
					
					params.stream()
							.map(x -> appContext.getBean(RoutineParamInstance.class, this, x))
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
