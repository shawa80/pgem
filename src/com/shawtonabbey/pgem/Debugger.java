package com.shawtonabbey.pgem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.ui.DebugWindow;

@Component
public class Debugger {

	@Autowired
	private DebugWindow win;
	
	@Autowired
	private EventDispatch dispatch;
	
	public Debugger() {
		
		
		
	}
	
	public void init() {
		dispatch.all.listeners().add((item, event)-> {
			win.setMessage("added " + item + "\n");
		});
		
		dispatch.save.listeners().add((name) -> {
			win.setMessage("file " + name + " saved");
		});
		
		dispatch.connectStart.listeners().add(() -> {
			win.setMessage("start\n");
		});
		dispatch.connectEnd.listeners().add(() -> {
			win.setMessage("stop\n");
		});
	}
	
}
