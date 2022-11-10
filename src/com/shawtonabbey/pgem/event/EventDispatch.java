package com.shawtonabbey.pgem.event;

import java.util.Hashtable;

import org.springframework.stereotype.Component;

@Component
public class EventDispatch {

	
	private Hashtable<Class<?>, Observable<?>> events = new Hashtable<>();
	
//	public <T> void register(Class<T> c) {
//		
//		//events.put(c, new Observable<T>(c));
//	}
	
	@SuppressWarnings("unchecked")
	public <T> Observable<T> find(Class<T> c) {
		
		if (!events.containsKey(c)) {
			events.put(c, new Observable<T>(c));
		}
		
		return (Observable<T>) events.get(c);
	}

	
}

