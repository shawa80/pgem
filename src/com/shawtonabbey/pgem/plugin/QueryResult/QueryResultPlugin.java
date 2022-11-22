package com.shawtonabbey.pgem.plugin.QueryResult;

import javax.swing.JScrollPane;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class QueryResultPlugin extends PluginBase {

	public void init() {
				
		
		dispatch.find(AQueryWindow.Added.class).listen((query, ev) -> {
			
			var table = new ResultsPane();
			
			var results = query.getResultPane();
			
			results.insertTab("results", null, new JScrollPane(table), "", 0);
			results.setSelectedIndex(0);
			
			query.dataReady.listen((model) -> {
				
				table.setModel(model);
				
			});
			
		});
		
	}
}
