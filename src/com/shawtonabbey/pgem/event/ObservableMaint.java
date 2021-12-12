package com.shawtonabbey.pgem.event;

public class ObservableMaint<T> {

	private Observable<T> pool;
	
	public ObservableMaint(Observable<T> pool) {
		this.pool = pool;
	}
	
	
	public void add(T toAdd) {
		pool.add(toAdd);
	}
	
	public void remove(T toRemove) {
		pool.remove(toRemove);
	}
	
}