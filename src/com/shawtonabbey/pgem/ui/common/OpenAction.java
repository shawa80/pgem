package com.shawtonabbey.pgem.ui.common;

import java.awt.Component;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.shawtonabbey.pgem.Openable;
import com.shawtonabbey.pgem.event.EventDispatch;

@org.springframework.stereotype.Component
@Scope("prototype")
public class OpenAction {
	
	@Autowired
	EventDispatch dispatch;
	
	public void perform(Component win, Openable op) {
		
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			@Override public boolean accept(File arg0) { return arg0.getName().endsWith("." + op.getExtn());}
			@Override public String getDescription() { return "*." + op.getExtn(); }
		});
		int returnVal = fc.showOpenDialog(win);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
		
			dispatch.open.getDispatcher().open(file.getAbsolutePath());
			
			try {
				var lines = Files.readAllLines(file.toPath(),
	                    Charset.defaultCharset());
				
				var content = String.join("\n", lines );
				op.setContent(content);
			} catch (Exception ex) {}
		}
	}
}
