package com.shawtonabbey.pgem.plugin.QueryMessage;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class QueryMessagePlugin extends PluginBase {


	public void init() {
				
		
		dispatch.find(AQueryWindow.Added.class).listen((query, ev) -> {
			
			var text = new JTextPane();
			
			var results = query.getResultPane();
			results.addTab("Messages", new JScrollPane(text));
		
			
			query.dataReady.listen((model) -> {
				
				var status = model.getStatus();
				var msg = status.getMessage() + "\nCount: " + status.getCount();
				text.setText(msg);
				
			});
			
		});
		
	}
}
