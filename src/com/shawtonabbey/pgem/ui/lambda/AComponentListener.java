package com.shawtonabbey.pgem.ui.lambda;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public interface AComponentListener extends ComponentListener {

	//not implemented componentShown
	public default void componentHidden(ComponentEvent arg0) {}

	public default void componentMoved(ComponentEvent arg0) {}

	public default void componentResized(ComponentEvent arg0) {}

	
}
