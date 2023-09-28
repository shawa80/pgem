package com.shawtonabbey.pgem.plugin.sqlhint;

import javax.swing.event.*;

public interface DocChange extends DocumentListener{

	
	public void Run(DocumentEvent arg0);
	
	@Override
	public default void changedUpdate(DocumentEvent arg0) {
		//Run(arg0);
	}

	@Override
	public default void insertUpdate(DocumentEvent arg0) {
		Run(arg0);
	}

	@Override
	public default void removeUpdate(DocumentEvent arg0) {
		Run(arg0);
	}

}
