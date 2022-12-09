package com.shawtonabbey.pgem.tree.view;
import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.view.DbView;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.DataGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class ViewInstance extends DataGroup<ViewGroup>
{
	@Getter
	private DbView view;

	public interface Added extends Add<ViewInstance> {}
		
	public ViewInstance (ViewGroup parent, DbView view)
	{
		super(parent, view.getName());
		this.view = view;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/view.png"));
	}

	@Override
	protected SwingWorker<?> getWorker() {
		return null;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Added.class).fire(o->o.added(this, event));
	}

	
}
