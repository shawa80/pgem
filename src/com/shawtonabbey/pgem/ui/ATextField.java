package com.shawtonabbey.pgem.ui;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ATextField extends JTextField {
	

	private static final long serialVersionUID = 1L;

	public interface TextChangeListener {
		public void textChanged(String text);
	}
	
	public void addTextChangeListener(TextChangeListener listener) {

		this.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) { listener.textChanged(ATextField.this.getText()); }

			@Override
			public void removeUpdate(DocumentEvent e) { listener.textChanged(ATextField.this.getText()); }

			@Override
			public void changedUpdate(DocumentEvent e) {}
			
		});

	}

}
