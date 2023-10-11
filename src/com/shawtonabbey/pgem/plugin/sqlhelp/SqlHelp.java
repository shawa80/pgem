package com.shawtonabbey.pgem.plugin.sqlhelp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.schema.DbSchemaFactory;
import com.shawtonabbey.pgem.database.table.DbTableFactory;
import com.shawtonabbey.pgem.database.view.DbViewFactory;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.plugin.sqlhint.DocChange;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;

@Component
public class SqlHelp extends PluginBase {

	@Autowired
	DbSchemaFactory schemas;
	
	@Autowired
	DbTableFactory tables;
	@Autowired
	DbViewFactory views;
	
	
	public SqlHelp() {
		
	}
	
	public void init() {
		dispatch.find(AQueryWindow.Added.class).listen((w, ev) -> {

			//move to worker thread
    		var m = new DefaultListModel<String>();	
    		var conn = w.getConnection();
    		var names = getHelp(conn);
    		m.addAll(names);
			
			var scrollPane = w.queryScroll;
			var layerPane = w.layer;
			var textPane = w.query;
			
    		var list = new JList<String>();

    		list.setModel(m);
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
	        	var o = e.getOffset()-4;
	        	var changeLen = e.getLength();
	        	try {
	        		
	        		var caret = textPane.getCaret();
	        		var xy = caret.getMagicCaretPosition();
	        		var fh = textPane.getFont().getSize();
	        		fh *= 1.5;
	        		
					var t = doc.getText(o, 4);
					if (("from".equals(t)
						|| "join".equals(t))
						&& changeLen == 1)
					{						
						
						var pos = scrollPane.getViewport().getViewPosition();
						listScroll.setLocation(xy.x - pos.x, (xy.y+fh)-pos.y);
						listScroll.setSize(200, 150);
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
	
	private List<String> getHelp(DBC conn) {
		
		var list = new ArrayList<String>();
		
		try {
			
			
			var sss = schemas.getSchemas(conn, false);
			for (var s : sss) {
				var jjj = tables.getTables(conn, s);
				var names = jjj.stream()
						.map((x) -> s.getName() + "." + x.getName())
						.collect(Collectors.toList());
				list.addAll(names);
				
				var lll = views.getViews(conn, s);
				names = lll.stream()
						.map((x) -> s.getName() + "." + x.getName())
						.collect(Collectors.toList());
				
				list.addAll(names);
				
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return list;
	}
}
