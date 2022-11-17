package com.shawtonabbey.pgem.ui;


import javax.swing.JPanel;

import com.shawtonabbey.pgem.database.DBC;

public interface MainWindow {
	
	public void launchQueryWin(DBC db, String query);
	public void launchPanel(JPanel panel);
	//public void launchImport();
	public void message(String msg);
}
