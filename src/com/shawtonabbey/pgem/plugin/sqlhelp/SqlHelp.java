package com.shawtonabbey.pgem.plugin.sqlhelp;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.sqlhint.DocChange;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;

@Component
public class SqlHelp extends PluginBase {

	public SqlHelp() {
		
	}
	
	public void init() {
		dispatch.find(AQueryWindow.Added.class).listen((w, ev) -> {
			
			var s = w.query.getStyledDocument();
			var x = w.query;
			var y = w.layer;
			var sp = w.queryScroll;

    		String[] data = {"one", "two", "three", "four", "x", "x", "x", "x"};
    		var list = new JList<String>(data);
    		var l = new JScrollPane(list);
    		y.add(l, JLayeredPane.POPUP_LAYER);

    		list.addListSelectionListener((e) -> {
    			//pause then h
    			if (!list.isSelectionEmpty())
    				new SwingWorker<Integer>()
    					.setWork(() -> { Thread.sleep(500); return 0;})
    					.thenOnEdt((r) -> l.setVisible(false))
    					.start();
    		});
			
	        w.query.getDocument().addDocumentListener((DocChange)(e) -> {
	        	var d = e.getDocument();
	        	var o = e.getOffset();
	        	try {
	        		
	        		var c = x.getCaret();
	        		var xy = c.getMagicCaretPosition();
	        		var fh = x.getFont().getSize();
	        		fh *= 1.5;
	        		
					var t = d.getText(o, 1);
					if ("x".equals(t))
					{
						var pos = sp.getViewport().getViewPosition();
						l.setLocation(xy.x - pos.x, (xy.y+fh)-pos.y);
						l.setSize(100, 100);
						l.setVisible(true);
						list.clearSelection();
					} 
					else 
						l.setVisible(false);
				} catch (Exception e1) {
				}
	        });
			
		});
	}
}
