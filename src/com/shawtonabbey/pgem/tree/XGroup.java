package com.shawtonabbey.pgem.tree;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.swingUtils.SwingWorkerChain;
import com.shawtonabbey.pgem.ui.tree.ItemModel;

public abstract class XGroup<P extends ItemModel> extends Group<P> {

	@Autowired
	protected ApplicationContext appContext;
	
	@Autowired
	protected EventDispatch dispatch;
	
	public XGroup(P parent, String name) {
		super(parent, name);

	}
	
	@SuppressWarnings("rawtypes")
	protected abstract SwingWorkerChain getWorker();
	
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
