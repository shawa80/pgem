package com.shawtonabbey.pgem.plugin.QueryResult;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class ResultsPane extends JTable {

	private static final long serialVersionUID = 1L;

	public ResultsPane() {
		super();
		
		getTableHeader().addMouseListener(
				new MouseAdapter(){

					@Override
					public void mouseClicked(MouseEvent mouseEvent) {
				        var cModel = getColumnModel();//cModel - column model
				        int selColumn = cModel.getColumnIndexAtX(mouseEvent.getX());
				        
				        if (selColumn >= 0) {
				          System.out.println("Clicked on column " + selColumn);
		                    //table.setColumnSelectionAllowed(true);
		                    //table.setRowSelectionAllowed(false);
		                    //table.clearSelection();
				          //table.setColumnSelectionInterval(selColumn, selColumn);
				        }
						
					}
		});
		
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setColumnSelectionAllowed(true);
		//table.setCellSelectionEnabled(true);
		setRowSelectionAllowed(true);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}
	
}
