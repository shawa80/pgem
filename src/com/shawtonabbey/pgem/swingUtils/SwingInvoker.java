package com.shawtonabbey.pgem.swingUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.swing.SwingUtilities;

public class SwingInvoker<T> {
	
	private final T target;
	private final T dispatcher;
	

	

	@SuppressWarnings("unchecked")
	public SwingInvoker(Class<T> targetClass, T target) {
		
		this.target = target;

		if (!targetClass.isInterface())
			throw new IllegalArgumentException("Target Class must be an interface");
		
		dispatcher = (T) Proxy.newProxyInstance(targetClass.getClassLoader(),
				new Class[] { targetClass }, new Dispatcher());
	}

	
	public static void invokeEx(RunnableEx target) throws Exception {
		
		new SwingInvoker<>(RunnableEx.class, target).getObject().run();
		
	}

	public static void invoke(Runnable target) {
		
		new SwingInvoker<>(Runnable.class, target).getObject().run();
	}

	public static void mustBeOnWorkThread() {
		if (SwingUtilities.isEventDispatchThread())
		{
			throw new RuntimeException("Running on EDT");
		}
	}
	
	
	public T getObject() {
		return dispatcher;
	}
	
	private class Dispatcher implements InvocationHandler {
		
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

			Object result = null;
			
			if (SwingUtilities.isEventDispatchThread())
				try {
					result = method.invoke(target, args);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			else {

				SwingUtilities.invokeLater(() -> {
					try {
					
						method.invoke(target, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						e.printStackTrace();
					} 
				});
			}
						
			return result;
		}

	}

}