package com.shawtonabbey.pgem.query;
import javax.swing.*;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.Openable;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerProxy;
import com.shawtonabbey.pgem.ui.common.OpenAction;
import com.shawtonabbey.pgem.ui.common.SaveAction;
import com.shawtonabbey.pgem.ui.lambda.AComponentListener;
import com.siliconandsynapse.patterns.observer.Observable;

//import jsyntaxpane.components.LineNumbersRuler;
//import jsyntaxpane.syntaxkits.SqlSyntaxKit;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.List;

@Component
@Scope("prototype")
public class AQueryWindow extends JPanel implements QueryWindow, Openable
{
	
	private static final long serialVersionUID = 1L;
	private RSyntaxTextArea query;
	private JScrollPane queryScroll;
	private SQLResultsPane results;
	private DBC conn;
	
	private String name;
	
	private SqlTableModel model;
	private JToolBar toolBar;
	private JButton stopButton;
	private JButton runButton;
	private JButton openButton;
	private JButton saveButton;
	
	@Autowired
	EventDispatch dispatch;
	
	@Autowired
	SaveAction save;
	
	@Autowired
	OpenAction open;
	
	public interface Event {public void event(QueryWindow win);}
	
	public final Observable<Event> runStart = new Observable<Event>(Event.class);
	public final Observable<Event> runFinished = new Observable<Event>(Event.class);
	
	public AQueryWindow()
	{
		this.name = "Query";

		model = new CountedRowTableModel();
		
		this.setLayout(new BorderLayout());

		var splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

		query = new RSyntaxTextArea();
		query.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
		query.setCodeFoldingEnabled(true);
		
		results = new SQLResultsPane(model);
		queryScroll = new JScrollPane(query);
		splitPane.setLeftComponent(queryScroll);
		splitPane.setRightComponent(results);

		add(splitPane, BorderLayout.CENTER);
		
		toolBar = new JToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		runButton = new JButton("Run");
		runButton.addActionListener((ev) -> {
			
			runStart.getDispatcher().event(this);
			
			var txt = query.getText();
			model.clear();
			
			new SwingWorkerProxy<>(SqlResultModel.class, model)
			.setWork((m) -> {
				conn.runQuery( 				//on Swing worker thread
						m::setColumns,	//on EDT, edtModel a proxy that of model that moves calls to EDT
						m::addRow,
						m::setStatus,
						txt);
				})
			.thenOnEdt((m) -> {
				runFinished.getDispatcher().event(this);
			}).start();
			
		});
		toolBar.add(runButton);
		
		stopButton = new JButton("Stop");
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				conn.stop();
			}
		});
		toolBar.add(stopButton);
				
		openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				open.perform(AQueryWindow.this, AQueryWindow.this);
			}
		});
		toolBar.add(openButton);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save.perform(AQueryWindow.this, AQueryWindow.this);
			}
		});
		toolBar.add(saveButton);
		
		
		setVisible(true);
				
		query.setText("");
		
		this.addComponentListener((AComponentListener)(e) -> {splitPane.setDividerLocation(0.5);});

	}

	public void init() {
		dispatch.queryWindow.getDispatcher().added(this, new com.shawtonabbey.pgem.tree.Event());
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

}
