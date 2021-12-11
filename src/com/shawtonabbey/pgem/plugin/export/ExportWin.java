package com.shawtonabbey.pgem.plugin.export;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.siliconandsynapse.patterns.observer.Observable;
import com.siliconandsynapse.patterns.observer.ObservableMaint;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ExportWin extends JDialog implements Logable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface Export { public void changed(String path, String query); }
	
	private final JFileChooser saveDialog = new JFileChooser();

	private Observable<Export> exportChange = new Observable<>(Export.class);
	private JTextArea exportLog;
	private JPanel queryPanel;
	private RSyntaxTextArea editorPane;
	public ExportWin() {
		
		saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("Comma Seperated Values (*.csv)", "csv"));
		saveDialog.setAcceptAllFileFilterUsed(false);
		
		setBounds(100, 100, 575, 390);
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new BorderLayout(0, 0));
		
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportWin.this.setVisible(false);
			}
		});
		buttonPanel.add(cancelBtn, BorderLayout.EAST);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		queryPanel = new JPanel();
		tabbedPane.addTab("Query", null, queryPanel, null);
		queryPanel.setLayout(new BorderLayout(0, 0));
		
		editorPane = new RSyntaxTextArea();
		editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
		editorPane.setCodeFoldingEnabled(true);
		queryPanel.add(editorPane);
		
		JPanel exportPanel = new JPanel();
		tabbedPane.addTab("Export", null, exportPanel, null);
		exportPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		exportPanel.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Csv File");
		panel.add(lblNewLabel, BorderLayout.WEST);
		
		JTextField csvFilename = new JTextField();
		panel.add(csvFilename, BorderLayout.CENTER);
		csvFilename.setColumns(10);
		
		JButton browseBtn = new JButton("Browse");
		browseBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int returnVal = saveDialog.showOpenDialog(ExportWin.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String path = saveDialog.getSelectedFile().getPath();
					csvFilename.setText(path);
				}
			}
		});
		panel.add(browseBtn, BorderLayout.EAST);
		
		JPanel panel_1 = new JPanel();
		exportPanel.add(panel_1, BorderLayout.SOUTH);
		
		JButton btnNewButton_1 = new JButton("Export");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exportChange.getDispatcher().changed(csvFilename.getText(), editorPane.getText());
			}
		});
		panel_1.add(btnNewButton_1);
		
		exportLog = new JTextArea();
		exportPanel.add(exportLog, BorderLayout.CENTER);
		exportLog.setColumns(10);
	}
	
	public void addMessage(String msg) {
		this.exportLog.append(msg);
	}
	
	public void setQuery(String query) {
		editorPane.setText(query);
	}
	
	public ObservableMaint<Export> getExportObserver() { return exportChange.getMaint(); }
}
