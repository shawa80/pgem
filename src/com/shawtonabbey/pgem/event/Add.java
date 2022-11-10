package com.shawtonabbey.pgem.event;

import com.shawtonabbey.pgem.tree.Event;

public interface Add<t> { 
	public void added(t newInstance, Event e);
}
