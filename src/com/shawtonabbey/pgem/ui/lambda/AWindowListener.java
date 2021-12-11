package com.shawtonabbey.pgem.ui.lambda;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public interface AWindowListener extends WindowListener {

	public default void windowActivated(WindowEvent e) {
		
	}
	
	public default void windowClosed(WindowEvent e)
	{
	}
	public default void windowDeactivated(WindowEvent e)
	{

	}
	public default void windowDeiconified(WindowEvent e)
	{
	}
	public default void windowIconified(WindowEvent e)
	{
	}
	public default void windowOpened(WindowEvent e)
	{
	}
	
	

}
