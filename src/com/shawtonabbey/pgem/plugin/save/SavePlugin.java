package com.shawtonabbey.pgem.plugin.save;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;

@Component
public class SavePlugin implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	SaveAction save;
	
	@Autowired
	OpenAction open;

	
	public void init() {
				
		dispatch.queryWindow.listen((m,ev) -> {
			
			
			var openButton = new JButton("Open");
			openButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					open.perform(m, m);
				}
			});
			m.addButton(openButton);

			
			var saveButton = new JButton("Save");
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					save.perform(m, m);
				}
			});			
			m.addButton(saveButton);
			
		});
		
	}
	
}

