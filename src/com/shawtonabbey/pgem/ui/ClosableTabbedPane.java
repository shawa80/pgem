package com.shawtonabbey.pgem.ui;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ClosableTabbedPane extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	public ClosableTabbedPane() {

        // Add panel to tab screen
        JPanel tab1Panel = new JPanel();
        addTab(null, null, tab1Panel, "");

        // Add label and button to the tab name
        //JPanel tab1 = new JPanel();
        //tab1.add(new JLabel("Tab Name"));
        //tab1.add(new JButton("x"));
        //setTabComponentAt(0, tab1);
        
               
    }
	
	public Component getSelectedTab() {
        return this.getTabComponentAt(this.getSelectedIndex());
	}
}