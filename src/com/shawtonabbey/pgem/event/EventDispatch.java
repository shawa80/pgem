package com.shawtonabbey.pgem.event;

import java.util.Hashtable;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.tree.Event;

@Component
public class EventDispatch {

	public interface Start {
		public void start();
	}
	public interface Stop {
		public void end();
	}
	

	public interface Open {
		public void open(String filename);
	}
	public interface Save {
		public void save(String filename);
	}

	
	public interface Add<t> { 
		public void added(t newInstance, Event e);
	}

	
	private Hashtable<Class<?>, Observable<?>> events = new Hashtable<>();
	
	public <T> void register(Class<T> c) {
		
		events.put(c, new Observable<T>(c));
	}
	@SuppressWarnings("unchecked")
	public <T> Observable<T> find(Class<T> c) {
		return (Observable<T>) events.get(c);
	}

	
}

