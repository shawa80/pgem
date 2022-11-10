package com.shawtonabbey.pgem.plugin;

import javax.swing.JMenuBar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.event.Open;
import com.shawtonabbey.pgem.event.Save;
import com.shawtonabbey.pgem.event.Start;
import com.shawtonabbey.pgem.event.Stop;

@Component
public class SysPlugin implements Plugin {

	public interface MenuEv extends Add<JMenuBar> {}
	public interface SaveEv extends Save {}
	public interface OpenEv extends Open {}
	public interface ConnectEv extends Start {}
	public interface DisconnectEv extends Stop {}
	
	@Autowired
	private EventDispatch dispatch;
	
	public void register() {

	}
	
	public void init() {
		
	}
}
