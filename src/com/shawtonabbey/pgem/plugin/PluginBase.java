package com.shawtonabbey.pgem.plugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;

@Component
public abstract class PluginBase implements Plugin {

	@Autowired
	protected EventDispatch dispatch;

	@Autowired
	protected ApplicationContext appContext;
	
}
