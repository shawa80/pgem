package com.shawtonabbey.pgem.plugin.debug;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;

@Component
public class DebuggerPlugin extends PluginBase {

	@Autowired
	private DebugWindow win;
	

	
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
		
		//win.setVisible(true);
	}
	
}
