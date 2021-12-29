package com.shawtonabbey.pgem.swingUtils;

import javax.swing.SwingUtilities;

import lombok.NonNull;

public class SwingWorkerChain<T> {

	public interface AppPreworkTask { public void run() throws Exception; }
	public interface AppWorkerTask<T> {	public T run() throws Exception; }
	public interface AppOnEdtTask<T> { public void run(T data) throws Exception; }
	public interface AppExceptionTask {	public void handle(Exception ex); }
	
	private AppPreworkTask prework = () -> {};
	private AppWorkerTask<T> work = () -> { return null; };
	private AppOnEdtTask<T> after = (e) -> {};
	private AppExceptionTask exception = (e) -> {};

	public SwingWorkerChain<T> setPrework(@NonNull AppPreworkTask prework) {

		this.prework = prework;
		return this;
	}

	
	public SwingWorkerChain<T> setWork(@NonNull AppWorkerTask<T> work) {

		this.work = work;
		return this;
	}
	
	public SwingWorkerChain<T> thenOnEdt(@NonNull AppOnEdtTask<T> after) {
		
		this.after = after;
		return this;
	}

	public SwingWorkerChain<T> setException(@NonNull AppExceptionTask handler) {

		this.exception = handler;
		return this;
	}

	
	public void start() {
	
		var t = new Thread(() -> {
			
			try {
				SwingUtilities.invokeLater(() -> {;
					try { prework.run(); } catch (Exception ex) {}
				});
				
				T result = work.run();
				
				SwingUtilities.invokeLater(() -> {
					
					try {
						after.run(result);
					} catch (Exception ex) {
						this.exception.handle(ex);
					}
					
				});
			} catch (Exception ex) {
				
				SwingUtilities.invokeLater(() -> {
					this.exception.handle(ex);						
				});
			}
			
		});
		t.setDaemon(true);
		t.setName("SwingWorkerChain");
		t.start();

	}
	
}
