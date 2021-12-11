package com.shawtonabbey.pgem.query;
import javax.swing.*;

public class SQLResultsPane extends JTabbedPane
{

	private static final long serialVersionUID = 1L;

	
	public SQLResultsPane(SqlTableModel model)
	{
		super(JTabbedPane.BOTTOM);		
		
		var table = new JTable(model);
		var text = new JTextPane();

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		//text.setText(message);
		removeAll();
		addTab("Results", new JScrollPane(table));
		addTab("Messages", new JScrollPane(text));

	
		model.addStatusListener((s) -> {
			
			var msg = s.getMessage() + "\nCount: " + s.getCount();
			
			text.setText(msg);
			
			if (s.getMessage().length() > 0) {
				this.setSelectedIndex(1);
			} else {
				this.setSelectedIndex(0);
			}
		});
	}

	
}
