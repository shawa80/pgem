package com.shawtonabbey.pgem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.EventDispatch;
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
		dispatch.all.getMaint().add((item, event)-> {
			win.setMessage("added " + item + "\n");
		});
		
		dispatch.saveListener.getMaint().add((name) -> {
			win.setMessage("file " + name + " saved");
		});
		
		dispatch.connectStartListener.getMaint().add(() -> {
			win.setMessage("start\n");
		});
		dispatch.connectEndListener.getMaint().add(() -> {
			win.setMessage("stop\n");
		});
	}
	
}
