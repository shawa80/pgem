package com.shawtonabbey.pgem.plugin.save;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.Savable;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class SavePlugin extends PluginBase {
	
	@Autowired
	SaveAction save;
	
	@Autowired
	OpenAction open;


	
	public void init() {
		
		dispatch.find(PgemMainWindow.Added.class).listen((w, ev) -> {
			
			JMenuItem menuItem;
			menuItem = new JMenuItem("Save");
			menuItem.addActionListener((ActionEvent e) ->
				{
					var qw = w.getDesktop().getSelectedComponent();
					var action = new SaveAction();
					
					if (qw instanceof Savable)
						action.perform(w, (Savable)qw);
					
				});

			menuItem.setMnemonic(KeyEvent.VK_Q);
			
			w.addMenu(menuItem);
			
		});
				
		dispatch.find(AQueryWindow.Added.class).listen((m,ev) -> {
			
			m.addAction("Open", (e)->open.perform(m, m));
			m.addAction("Save", (e)->save.perform(m, m));	
		});
		
	}
	
}

