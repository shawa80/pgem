package com.shawtonabbey.pgem.plugin.sqlhelp;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.sqlhint.DocChange;
import com.shawtonabbey.pgem.query.AQueryWindow;

@Component
public class SqlHelp extends PluginBase {

	public SqlHelp() {
		
	}
	
	public void init() {
		dispatch.find(AQueryWindow.Added.class).listen((w, ev) -> {
			
			var s = w.query.getStyledDocument();
			var x = w.query;
			
	        w.query.getDocument().addDocumentListener((DocChange)(e) -> {
	        	var d = e.getDocument();
	        	var o = e.getOffset();
	        	try {
	        		String[] data = {"one", "two", "three", "four"};
	        		var p = new JPopupMenu();
	        		p.add("test");
	        		var l = new JList<String>(data);
	        		
	        		var c = x.getCaret();
	        		var xy = c.getMagicCaretPosition();
	        		var fh = x.getFont().getSize();
	        		fh *= 1.5;
	        		
					var t = d.getText(o, 1);
					if ("x".equals(t))
	        			p.show(x, xy.x, xy.y+fh);
					//System.out.println("--" + p.x + " " + p.y);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        });
			
		});
	}
}
