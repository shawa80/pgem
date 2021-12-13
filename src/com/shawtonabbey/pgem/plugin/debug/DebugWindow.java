package com.shawtonabbey.pgem.plugin.debug;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.springframework.stereotype.Component;

@Component
public class DebugWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	
	public DebugWindow() {
		
		textArea = new JTextArea();
		getContentPane().add(new JScrollPane(textArea), BorderLayout.CENTER);
	}
	
	public void setMessage(String text) {
		textArea.append(text);
	}

}
