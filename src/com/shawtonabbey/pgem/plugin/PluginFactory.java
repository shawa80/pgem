package com.shawtonabbey.pgem.plugin;

import org.springframework.context.support.AbstractApplicationContext;

public interface PluginFactory {

	public Plugin load(AbstractApplicationContext context);
	
}
