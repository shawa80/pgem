package com.shawtonabbey.pgem.query;
import javax.swing.*;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.Openable;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.ui.lambda.AComponentListener;

//import jsyntaxpane.components.LineNumbersRuler;
//import jsyntaxpane.syntaxkits.SqlSyntaxKit;

import java.awt.BorderLayout;
import java.awt.event.*;

@Component
@Scope("prototype")
public class AQueryWindow extends JPanel implements QueryWindow, Openable
{
	
	private static final long serialVersionUID = 1L;
	public JTextPane query;
	//public RSyntaxTextArea query;
	private JScrollPane queryScroll;
	private SQLResultsPane results;
	private DBC conn;
	
	private String name;
	
	private SqlTableModel model;
	private JToolBar toolBar;
	
	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<AQueryWindow> {}
	

	public final Observable<QueryStarted> runStart = new Observable<>(QueryStarted.class);
	public final Observable<QueryEnded> runFinished = new Observable<>(QueryEnded.class);
	public final Observable<DataReady> dataReady = new Observable<>(DataReady.class);


	public SqlTableModel getModel() {
		return model;
	}
	
	public AQueryWindow()
	{
		this.name = "Query";

		model = new CountedRowTableModel();
		
		this.setLayout(new BorderLayout());

		var splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		query = new JTextPane();
		//query = new RSyntaxTextArea();
		//query.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
		//query.setCodeFoldingEnabled(true);
		
		results = new SQLResultsPane();
		queryScroll = new JScrollPane(query);
		splitPane.setLeftComponent(queryScroll);
		splitPane.setRightComponent(results);

		add(splitPane, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);				
		
		setVisible(true);
				
		query.setText("");
		
		this.addComponentListener((AComponentListener)(e) -> {splitPane.setDividerLocation(0.5);});

	}

	
	public void init() {
		dispatch.find(Added.class).fire(o->o.added(this, new com.shawtonabbey.pgem.tree.Event()));
	}
	
	public void addAction(String name, ActionListener action) {
		var btn = new JButton(name);
		btn.addActionListener(action);
		toolBar.add(btn);
	}
	
	@Override
	public void setConnection(DBC c) {
		conn = c;
		this.name = "Query [" + c.getDatabase() + "]";
	}
	
	public DBC getConnection() {
		return conn;
	}
	
	@Override
	public void setSql(String sql) {
		query.setText(sql);
	}
	
	@Override 
	public String getSql() {
		return query.getText();
	}
	
	public String toString() {
		return name;
	}
	
	@Override
	public void enableSql() {
		
//		query.setEditorKit(new SqlSyntaxKit());
//		LineNumbersRuler r = new LineNumbersRuler();
//		r.install(query);
//		queryScroll.setRowHeaderView(r);	

	}

	@Override
	public String getSavable() {
		return getSql();
	}

	@Override
	public String getExtn() {
		return "sql";
	}

	@Override
	public void setContent(String content) {
		
		setSql(content);
		
	}

	public SQLResultsPane getResultPane() {
		return this.results;
	}
	
}
