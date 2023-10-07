package com.shawtonabbey.pgem.plugin.sqlhelp;

import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;
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

    		String[] data = {"one", "two", "three", "four", "x", "x", "x", "x"};
			
			var scrollPane = w.queryScroll;
			var layerPane = w.layer;
			var textPane = w.query;
			
    		var list = new JList<String>(data);
    		var listScroll = new JScrollPane(list);
    		
    		layerPane.add(listScroll, JLayeredPane.POPUP_LAYER);

    		list.addListSelectionListener((e) -> {

    			if (!list.isSelectionEmpty() && e.getValueIsAdjusting() == false)
    				new SwingWorker<Integer>()
    					.setWork(() -> { Thread.sleep(250); return 0;})
    					.thenOnEdt((r) -> {
    						var item = list.getSelectedValue();
    						listScroll.setVisible(false);
    						var caretPos = textPane.getCaretPosition();
    						textPane.getDocument().insertString(caretPos, item, null);
    						var size = item.length();
    						textPane.setCaretPosition(caretPos + size);
    						textPane.requestFocus();
    					})
    					.start();
    		});
			
	        w.query.getDocument().addDocumentListener((DocChange)(e) -> {
	        	var doc = e.getDocument();
	        	var o = e.getOffset()-5;
	        	try {
	        		
	        		var caret = textPane.getCaret();
	        		var xy = caret.getMagicCaretPosition();
	        		var fh = textPane.getFont().getSize();
	        		fh *= 1.5;
	        		
					var t = doc.getText(o, 5);
					if ("from ".equals(t))
					{
						var pos = scrollPane.getViewport().getViewPosition();
						listScroll.setLocation(xy.x - pos.x, (xy.y+fh)-pos.y);
						listScroll.setSize(100, 100);
						listScroll.setVisible(true);
						list.clearSelection();
					} 
					else 
						listScroll.setVisible(false);
				} catch (Exception e1) {
				}
	        });
			
		});
	}
}
