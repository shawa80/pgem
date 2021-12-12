package com.shawtonabbey.pgem.event;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
	
	private final List<T> observers;
	private final T dispatcher;
	private ObservableMaint<T> maint;
	

	@SuppressWarnings("unchecked")
	public Observable(Class<?> observerClass) {
		maint = new ObservableMaint<T>(this);
		observers = new ArrayList<T>();
		dispatcher = (T) Proxy.newProxyInstance(observerClass.getClassLoader(),
				new Class[] { observerClass }, new Dispatcher());
	}


	public ObservableMaint<T> getMaint() {
		return maint;
	}
	
	
	void add(T observer) {
		synchronized (observers) {
			observers.add(observer);
		}
	}

	void remove(T observer) {
		synchronized (observers) {
			observers.remove(observer);
		}
	}

	public T getDispatcher() {
		return dispatcher;
	}

	public boolean hasObservers() {
		return !observers.isEmpty();
	}

	private class Dispatcher implements InvocationHandler {
		
		public Object invoke(final Object proxy, final Method method,
				final Object[] args) throws Throwable {
			List<Throwable> exceptions = new ArrayList<Throwable>();
			return dispatchToObservers(method, exceptions, args);
		}

		private Object dispatchToObservers(Method method,
				List<Throwable> exceptionsThrown, Object... args) {
			try {
				synchronized (observers) {
					for (Object observer : observers) {
						try {
							method.invoke(observer, args);
						} catch (RuntimeException re) {
							exceptionsThrown.add(re);
						} catch (Exception e) {
							exceptionsThrown.add(e);
						}
					}
				}
			} catch (IllegalArgumentException e) {
				exceptionsThrown.add(e);
			}

			if (exceptionsThrown.isEmpty()) {
				return null;
			} else {
				throw new DispatchException(exceptionsThrown);
			}
		}

	}

	public static class DispatchException extends RuntimeException {

		private static final long serialVersionUID = -2398778507646541435L;
		private final List<Throwable> exceptionsThrown;

		public DispatchException(List<Throwable> exceptionsThrown) {
			this.exceptionsThrown = exceptionsThrown;
		}

		public List<Throwable> getExceptionsThrown() {
			return exceptionsThrown;
		}
	}
}
