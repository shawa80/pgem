package com.shawtonabbey.pgem.ui.lambda;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public interface AComponentListenerAll extends ComponentListener {

	
	public void run(ComponentEvent arg0);
	
	public default void componentShown(ComponentEvent arg0) {run(arg0);}
	public default void componentHidden(ComponentEvent arg0) {run(arg0);}

	public default void componentMoved(ComponentEvent arg0) {run(arg0);}

	public default void componentResized(ComponentEvent arg0) {run(arg0);}

	
}
