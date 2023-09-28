package com.shawtonabbey.pgem.plugin.sqlhint;

import java.awt.Color;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class SqlHint  extends PluginBase {
	
	private SimpleAttributeSet keyword;
	private SimpleAttributeSet normal;
	
	public SqlHint() {
		
        keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, Color.blue);
        StyleConstants.setBackground(keyword, Color.white);

        normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.black);
        StyleConstants.setBackground(normal, Color.white);
	}
	
	
	public void init() {
		
		dispatch.find(AQueryWindow.Added.class).listen((w, ev) -> {
			
			
			var s = w.query.getStyledDocument();
			UndoManager manager = new UndoManager();
			w.query.getDocument().addUndoableEditListener(manager);
			
	        w.query.getDocument().addDocumentListener((DocChange)(e) -> {
	        	set(s, e);
	        });
			
		});
	}
	
	private void set(StyledDocument s, DocumentEvent e) {
	
		
		var docLength = s.getLength();
		String text="";
		try {
			text = s.getText(0, docLength);
		} catch (BadLocationException ex) {
		}
		
		var indexes = findIndexes(text, "select");
		indexes.addAll(findIndexes(text, "from"));
		indexes.addAll(findIndexes(text, "where"));
		indexes.addAll(findIndexes(text, "join"));
		
		SwingUtilities.invokeLater(() -> {
			s.setCharacterAttributes(0, docLength, normal, true);
			for(var x : indexes)
				s.setCharacterAttributes(x.getStart(), x.getLength(), keyword, true);
		});
	}
	
	private ArrayList<Selection> findIndexes(String text, String keyword) {
		
		int size = keyword.length();
		var indexes = new ArrayList<Selection>();
		var pattern = Pattern.compile("(?:\\s|^|\\(|\\))(" + keyword + ")(?:$|\\s|\\(|\\))", Pattern.CASE_INSENSITIVE);
	    var matcher = pattern.matcher(text);
	   
	      while (matcher.find()) {
	    	  indexes.add(new Selection(matcher.start(1), size));
	       }
		return indexes;
	}
}