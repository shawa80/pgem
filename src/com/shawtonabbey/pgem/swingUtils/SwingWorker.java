package com.shawtonabbey.pgem.swingUtils;
//package com.shawtonabbey.pgem.query.swingUtils;
//
///**
// * Wraps the target in a proxy that makes EDT save calls
// * 
// * @author shawa
// *
// * @param <T>
// */
//public class SwingWorker<T> {
//
//	private T proxy;
//	
//	public SwingWorker(Class<T> targetClass, T target) {
//		
//		proxy = new SwingInvoker<>(targetClass, target).getObject();
//		
//	}
//	
//	public void startThread(Worker<T> w) {
//	
//		var t = new Thread(() -> {
//			w.run(proxy);
//		});
//		t.setDaemon(true);
//		t.setName("SwingWorker");
//		t.start();
//	}
//
//	
//	public interface Worker<T> {
//		
//		public void run(T data);
//	}
//	
//}
