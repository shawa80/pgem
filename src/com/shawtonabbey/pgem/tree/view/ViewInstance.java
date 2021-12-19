package com.shawtonabbey.pgem.tree.view;
import javax.swing.ImageIcon;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DbView;
import com.shawtonabbey.pgem.event.EventDispatch.Add;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.XGroup;

import lombok.Getter;

@Component
@Scope("prototype")
public class ViewInstance extends XGroup<ViewGroup>
{
	@Getter
	private DbView view;

	
	public interface Ev extends Add<ViewInstance> {}
		
	public ViewInstance (ViewGroup parent, DbView view)
	{
		super(parent, view.getName());
		this.view = view;
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/view.png"));
	}

	@Override
	protected SwingWorkerChain<?> getWorker() {
		return null;
	}

	@Override
	protected void FireEvent(Event event) {
		dispatch.find(Ev.class).fire(o->o.added(this, event));
	}

	
}
