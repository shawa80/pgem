package com.shawtonabbey.pgem.plugin.QueryResult;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class QueryResultPlugin extends PluginBase {

	public void init() {
				
		
		dispatch.find(AQueryWindow.Added.class).listen((query, ev) -> {
			
			var table = new JTable();
			
			table.getTableHeader().addMouseListener(
					new MouseAdapter(){

						@Override
						public void mouseClicked(MouseEvent mouseEvent) {
					        var cModel = table.getColumnModel();//cModel - column model
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
			
			
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			var results = query.getResultPane();
			results.addTab("Results", new JScrollPane(table));
			
			table.setColumnSelectionAllowed(true);
			//table.setCellSelectionEnabled(true);
			table.setRowSelectionAllowed(true);
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			
			query.dataReady.listen((model) -> {
				
				table.setModel(model);
				
			});
			
		});
		
	}
}
