package com.shawtonabbey.pgem.event;

public class Listeners<T> {

	private Observable<T> pool;
	
	public Listeners(Observable<T> pool) {
		this.pool = pool;
	}
	
	
	public void add(T toAdd) {
		pool.add(toAdd);
	}
	
	public void remove(T toRemove) {
		pool.remove(toRemove);
	}
	
}