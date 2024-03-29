package com.shawtonabbey.pgem.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

public abstract class DataGroup<P extends ItemModel> extends Group<P> {

	@Autowired
	protected ApplicationContext appContext;
	
	@Autowired
	protected EventDispatch dispatch;
	
	public DataGroup(P parent, String name) {
		super(parent, name);

	}
	
	@SuppressWarnings("rawtypes")
	protected abstract SwingWorker getWorker();
	
	protected abstract void FireEvent(Event event);
	
	public void refresh(Event event) {
		
		var sw = getWorker();
		if (sw != null) {
			sw.setPrework(() -> {
				removeAll(); 
				setLoading();
			})
			.setException((ex)->{
				setError();
			});
			sw.start();
		}
	}
	
	public ItemModel load(Event event) {

		event.lock(this);
		FireEvent(event);
		event.unlock(this);	
		
		var sw = getWorker();
		if (sw != null) {
			sw.setPrework(() -> {
				removeAll(); 
				setLoading();
			})
			.setException((ex)->{
				setError();
			});
			this.AddWillExpandListener(this, () -> sw.start());
		}
			
				
		return this;
	}

}
