package com.shawtonabbey.pgem.plugin.QueryResult;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class QueryResultPlugin extends PluginBase {

	public void init() {
				
		
		dispatch.find(AQueryWindow.Added.class).listen((query, ev) -> {
			
			var table = new JTable();
			table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			var results = query.getResultPane();
			results.addTab("Results", new JScrollPane(table));
			
			query.dataReady.listen((model) -> {
				
				table.setModel(model);
				
			});
			
		});
		
	}
}
