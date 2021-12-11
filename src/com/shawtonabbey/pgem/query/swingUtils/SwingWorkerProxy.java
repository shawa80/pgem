package com.shawtonabbey.pgem.query.swingUtils;

import javax.swing.SwingUtilities;

import lombok.NonNull;

public class SwingWorkerProxy<T> {

	
	public interface AppWorkerTask<T> { public void run(T data) throws Exception; }
	public interface AppOnEdtTask<T> { public void run(T data) throws Exception; }
	public interface AppExceptionTask {	public void handle(Exception ex); }
	
	private AppWorkerTask<T> work = (e) -> {};
	private AppOnEdtTask<T> after = (e) -> {};
	private AppExceptionTask exception = (e) -> {};
	
	private T proxy;
	
	public SwingWorkerProxy(Class<T> targetClass, T proxy) {
		this.proxy = proxy;
	}
	
	public SwingWorkerProxy<T> setWork(@NonNull AppWorkerTask<T> work) {

		this.work = work;
		return this;
	}
	
	public SwingWorkerProxy<T> thenOnEdt(@NonNull AppOnEdtTask<T> after) {
		
		this.after = after;
		return this;
	}

	public SwingWorkerProxy<T> setException(@NonNull AppExceptionTask handler) {
		
		this.exception = handler;
		return this;
	}

	
	public void start() {
	
		var t = new Thread(() -> {
			
			try {
				work.run(proxy);
				
				SwingUtilities.invokeLater(() -> {
					try {
						after.run(proxy);
					} catch (Exception ex) {
						exception.handle(ex);
					}
				});
			} catch (Exception ex) {
				
				SwingUtilities.invokeLater(() -> {
					exception.handle(ex);						
				});	
			}
		});
		t.setDaemon(true);
		t.setName("SwingWorkerProxy");
		t.start();

	}
	
}
