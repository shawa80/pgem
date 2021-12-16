package com.shawtonabbey.pgem.plugin.save;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.shawtonabbey.pgem.Savable;
import com.shawtonabbey.pgem.SysPlugin;
import com.shawtonabbey.pgem.event.EventDispatch;

@org.springframework.stereotype.Component
@Scope("prototype")
public class SaveAction {

	@Autowired
	EventDispatch dispatch;
	
	public void perform(Component win, Savable qw) {
		
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			@Override public boolean accept(File arg0) { return arg0.getName().endsWith("." + qw.getExtn());}
			@Override public String getDescription() { return "*." + qw.getExtn(); }
		});
		int returnVal = fc.showSaveDialog(win);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			dispatch.find(SysPlugin.SaveEv.class).fire(o->o.save(file.getAbsolutePath()));
			
			try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
				pw.write(qw.getSavable());
			} catch (IOException e1) {}
		}
	}
	
}
