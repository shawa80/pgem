package com.shawtonabbey.pgem.plugin.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class DebuggerPlugin implements Plugin {

	@Autowired
	private DebugWindow win;
	
	@Autowired
	private EventDispatch dispatch;
	
	public DebuggerPlugin() {
		
		
		
	}
	
	public void register() {
	}

	
	public void init() {
//		dispatch.all.listen((item, event)-> {
//			win.setMessage("added " + item + "\n");
//		});
//		
//		dispatch.save.listen((name) -> {
//			win.setMessage("file " + name + " saved");
//		});
//		
//		dispatch.connectStart.listen(() -> {
//			win.setMessage("start\n");
//		});
//		dispatch.connectEnd.listen(() -> {
//			win.setMessage("stop\n");
//		});
		
		win.setVisible(true);
	}
	
}
