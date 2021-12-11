package com.shawtonabbey.pgem;

import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class SaveAction {

	
	public void perform(Component win, Savable qw) {
		
		final JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileFilter() {
			@Override public boolean accept(File arg0) { return arg0.getName().endsWith("." + qw.getExtn());}
			@Override public String getDescription() { return "*." + qw.getExtn(); }
		});
		int returnVal = fc.showSaveDialog(win);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			
			System.out.println(qw.getSavable());
			
			try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
				pw.write(qw.getSavable());
			} catch (IOException e1) {}
		}
	}
	
}
