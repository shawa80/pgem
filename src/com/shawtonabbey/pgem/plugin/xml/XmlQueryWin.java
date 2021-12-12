package com.shawtonabbey.pgem.plugin.xml;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.TableModel;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.awt.event.ActionEvent;

import com.shawtonabbey.pgem.Savable;
import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.query.CountedRowTableModel;
import com.shawtonabbey.pgem.query.SQLResultsPane;
import javax.swing.JTextField;

@Component
@Scope("prototype")
public class XmlQueryWin extends JPanel implements Savable{

	private static final long serialVersionUID = 1L;

	private JTable table;
	
	public final Observable<Runnable> runListener = new Observable<>(Runnable.class);
	private RSyntaxTextArea xmlDoc;
	private JTextPane xPathQuery;
	private CountedRowTableModel resultsModel;
	private JTextField docFilePath;
	
	/**
	 * Create the panel.
	 */
	public XmlQueryWin() {
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane.setLeftComponent(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("xPath", null, panel_1, null);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		xPathQuery = new JTextPane();
		panel_1.add(new JScrollPane(xPathQuery), BorderLayout.CENTER);
		
		JToolBar toolBar = new JToolBar();
		panel_1.add(toolBar, BorderLayout.NORTH);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				runListener.getDispatcher().run();
			}
		});
		toolBar.add(btnRun);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Namespace", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		table = new JTable();
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel_2.add(tabbedPane_1, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		tabbedPane_1.addTab("Doc", null, panel_3, null);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		xmlDoc = new RSyntaxTextArea();
		xmlDoc.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML);
		xmlDoc.setCodeFoldingEnabled(true);
		panel_3.add(new JScrollPane(xmlDoc));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		docFilePath = new JTextField();
		panel_4.add(docFilePath);
		docFilePath.setColumns(10);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(XmlQueryWin.this);
				
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
			            var file = fc.getSelectedFile();
			            docFilePath.setText(file.getAbsolutePath());
			            var xmlDocStr = "";
			            try {
			            	try (Stream<String> stream = Files.lines(file.toPath())) {
			            		xmlDocStr = stream.collect(Collectors.joining("\n"));
			            	}
			            }catch (Exception ex) {}
			        xmlDoc.setText(xmlDocStr);
			     }
			}
		});
		panel_4.add(btnBrowse, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		tabbedPane_1.addTab("Results", null, panel_5, null);
		
		resultsModel = new CountedRowTableModel();
		panel_5.setLayout(new BorderLayout(0, 0));
		SQLResultsPane resultsPane = new SQLResultsPane(resultsModel);
		panel_5.add(resultsPane);
		
		JToolBar toolBar_1 = new JToolBar();
		panel_5.add(toolBar_1, BorderLayout.NORTH);
		
		JButton btnNewButton_1 = new JButton("New button");
		toolBar_1.add(btnNewButton_1);

	}
	
	@Override
	public String toString() {
		return "XPath";
	}
	
	public String getDoc () {
		return this.xmlDoc.getText();
	}
	
	public String getXpath() {
		return this.xPathQuery.getText();
	}
	
	public void setResult(String r) {
		
	}
	
	public void setModel(TableModel m) {
		table.setModel(m);	
	}
	
	public CountedRowTableModel getResultsModel() {
		return resultsModel;
	}

	@Override
	public String getSavable() {
		
		return "Xpath text";
	}

	@Override
	public String getExtn() {
		return "xpath";
	}
}
