package com.shawtonabbey.pgem.tree;

import java.util.HashSet;
import java.util.Hashtable;

import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.query.swingUtils.SwingInvoker;

import lombok.Getter;

public class Event {

	private HashSet<Object> locks = new HashSet<>();
	private Observable<Runnable> whenDone = new Observable<>(Runnable.class);
	
	@Getter
	private Hashtable<String, Object> params = new Hashtable<>();
	
	public void lock(Object lock) {
		locks.add(lock);
	}
	
	public void unlock(Object lock) {
		locks.remove(lock);
		
		if (locks.size() == 0) {
		
			SwingInvoker.invoke(() -> {		
				whenDone.getDispatcher().run();				
			});

		}
	}
	
	public void whenFinished(Runnable run) {
		
		whenDone.getMaint().add(run);	
	}
	
}
