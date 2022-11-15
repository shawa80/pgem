package com.shawtonabbey.pgem.plugin.ddl.index;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JSplitPane;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.table.DbTable;

import javax.swing.JEditorPane;

public class IndexCreatePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	//private DbTable table;
	private IndexInput indexInput;
	
	//@Autowired
	//private DebugWindow debug;
	private JEditorPane editorPane;

	
	public IndexCreatePanel(DbTable table, List<DbColumn> cols) {
		this();
		//this.table = table;
		
		
		var model = new AbstractIndexModel();
	
		
		model.AddListener((i) -> {
			
			editorPane.setText(IndexWriter.write(model));
			
		});
		
		indexInput.setTable(table, cols);
		indexInput.setModel(model);
		indexInput.init();
	}
	
	

	
	public IndexCreatePanel() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		indexInput = new IndexInput();
		splitPane.setLeftComponent(indexInput);
		
		JPanel panel = new JPanel();
		splitPane.setRightComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		editorPane = new JEditorPane();
		panel.add(new JScrollPane(editorPane), BorderLayout.CENTER);

	}

	@Override
	public String toString() {
		return "Create index";
	}
	
}
