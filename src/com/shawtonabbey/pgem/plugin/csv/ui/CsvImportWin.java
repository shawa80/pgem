package com.shawtonabbey.pgem.plugin.csv.ui;


import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.ui.lambda.AComponentListener;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

public class CsvImportWin extends JDialog {

	private static final long serialVersionUID = 1L;

	public interface CsvPicked { public void changed(String path); }
	public interface Compile { public void build(); }
	public interface Builder { public void build(); }
	public interface Saver { public void save(String path); }
	public interface Loader { public void load(String path); }
	
	private final JPanel contentPanel = new JPanel();
	private JTextField csvFileName;
	private RSyntaxTextArea csvPane;
	private RSyntaxTextArea compilePane;
	private JEditorPane executionPane;
	private RSyntaxTextArea codePane;
	private JScrollPane codeScrollPane;
	private JScrollPane csvScrollPane;
	
	private JPanel compilePanel;
	
	private final JFileChooser fc = new JFileChooser();
	private final JFileChooser saveDialog = new JFileChooser();
	private final JFileChooser openDialog = new JFileChooser();
	
	private Observable<CsvPicked> csvChange = new Observable<>(CsvPicked.class);
	private Observable<Compile> csvCompile = new Observable<>(Compile.class);
	private Observable<Builder> csvBuilder = new Observable<>(Builder.class);
	private Observable<Saver> csvSaver = new Observable<>(Saver.class);
	private Observable<Loader> csvLoader = new Observable<>(Loader.class);
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CsvImportWin dialog = new CsvImportWin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create the dialog.
	 */
	public CsvImportWin() {

		fc.addChoosableFileFilter(new FileNameExtensionFilter("Comma seperated values (*.csv)", "csv"));
		fc.setAcceptAllFileFilterUsed(false);
		
		saveDialog.addChoosableFileFilter(new FileNameExtensionFilter("Import Config (*.icx)", "icx"));
		saveDialog.setAcceptAllFileFilterUsed(false);
		
		openDialog.addChoosableFileFilter(new FileNameExtensionFilter("Import Config (*.icx)", "icx"));
		openDialog.setAcceptAllFileFilterUsed(false);
		
		setBounds(100, 100, 575, 390);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BorderLayout(0, 0));
		}
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
				panel.add(tabbedPane, BorderLayout.CENTER);
				{
					JPanel panel_1 = new JPanel();
					tabbedPane.addTab("Csv", null, panel_1, null);
					panel_1.setLayout(new BorderLayout(0, 0));
					{
						JPanel csvFilePane = new JPanel();
						panel_1.add(csvFilePane, BorderLayout.NORTH);
						csvFilePane.setLayout(new BorderLayout(0, 0));
						{
							JLabel lblNewLabel = new JLabel("Csv File");
							lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
							csvFilePane.add(lblNewLabel, BorderLayout.WEST);
						}
						{
							csvFileName = new JTextField();
							csvFilePane.add(csvFileName);
							csvFileName.setColumns(10);
						}
						{
							JButton btnNewButton = new JButton("Browse");
							csvFilePane.add(btnNewButton, BorderLayout.EAST);
							btnNewButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {

									int returnVal = fc.showOpenDialog(CsvImportWin.this);
									if (returnVal == JFileChooser.APPROVE_OPTION) {
										String path = fc.getSelectedFile().getPath();
										csvFileName.setText(path);
										csvChange.fire(o->o.changed(path));
									}
								}
							});
						}
					}
					{
						csvPane = new RSyntaxTextArea();
						csvPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						csvPane.setCodeFoldingEnabled(true);
						csvScrollPane = new JScrollPane(csvPane);
						panel_1.add(csvScrollPane);
					}
				}
				{
					compilePanel = new JPanel();
					compilePanel.setLayout(new BorderLayout(0, 0));
					{
						compilePane = new RSyntaxTextArea();
						compilePane.setFont(new Font("Consolas", Font.PLAIN, 11));
						JScrollPane compileScrollPane = new JScrollPane(compilePane);
						compilePanel.add(compileScrollPane, BorderLayout.CENTER);
					}
					{
						JPanel panel_1_1 = new JPanel();
						compilePanel.add(panel_1_1, BorderLayout.SOUTH);
						{
							JButton okButton = new JButton("Compile");
							panel_1_1.add(okButton);
							okButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									csvCompile.fire(o->o.build());
								}
							});
							okButton.setActionCommand("OK");
							getRootPane().setDefaultButton(okButton);
						}
					}
				}
				{
					JPanel panel_1 = new JPanel();
					tabbedPane.addTab("Transform", null, panel_1, null);
					panel_1.setLayout(new BorderLayout(0, 0));
					{
						JPanel transformPane = new JPanel();
						panel_1.add(transformPane, BorderLayout.NORTH);
						transformPane.setLayout(new BorderLayout(0, 0));
						{
							JLabel lblNewLabel_1 = new JLabel("Table");
							transformPane.add(lblNewLabel_1, BorderLayout.WEST);
						}
					}
					{
						codePane = new RSyntaxTextArea();
						codePane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
						codePane.setCodeFoldingEnabled(true);
						codeScrollPane = new JScrollPane(codePane);
						//panel_1.add(codeScrollPane);
					}
					{
						JSplitPane transformSplitPane = new JSplitPane();
						transformSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
						panel_1.add(transformSplitPane, BorderLayout.CENTER);
						transformSplitPane.setTopComponent(codeScrollPane);
						transformSplitPane.setBottomComponent(compilePanel);
						this.addComponentListener((AComponentListener)(e) -> {transformSplitPane.setDividerLocation(0.5);});
					}
					
				}
				
				{
					JPanel panel_1 = new JPanel();
					tabbedPane.addTab("Execute", null, panel_1, null);
					panel_1.setLayout(new BorderLayout(0, 0));
					{
						executionPane = new JEditorPane();
						JScrollPane executionScrolPane = new JScrollPane(executionPane);
						panel_1.add(executionScrolPane, BorderLayout.CENTER);
					}
					{
						JPanel panel_2 = new JPanel();
						panel_1.add(panel_2, BorderLayout.SOUTH);
						{
							JButton btnNewButton_1 = new JButton("Execute");
							btnNewButton_1.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									csvBuilder.fire(o->o.build());
								}
							});
							panel_2.add(btnNewButton_1);
						}
					}
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						CsvImportWin.this.setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnFile = new JMenu("File");
				menuBar.add(mnFile);
				{
					JMenuItem mntmOpen = new JMenuItem("Open");
					mntmOpen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int returnVal = openDialog.showOpenDialog(CsvImportWin.this);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								String path = openDialog.getSelectedFile().getPath();
								csvLoader.fire(o->o.load(path));
							}
						}
					});
					mnFile.add(mntmOpen);
				}
				{
					JMenuItem mntmSave = new JMenuItem("Save");
					mntmSave.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							int returnVal = saveDialog.showSaveDialog(CsvImportWin.this);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								String path = saveDialog.getSelectedFile().getPath();
								csvSaver.fire(o->o.save(path));
							}

							
						}
					});
					mnFile.add(mntmSave);
				}
			}
		}
	}

	public void enableJava() {
	
//		{
//			codePane.setEditorKit(new JavaSyntaxKit());
//			LineNumbersRuler r = new LineNumbersRuler();
//			r.install(codePane);
//			codeScrollPane.setRowHeaderView(r);
//		}
//		{			
//			csvPane.setEditorKit(new JavaSyntaxKit());
//			LineNumbersRuler r = new LineNumbersRuler();
//			r.install(csvPane);
//			csvScrollPane.setRowHeaderView(r);	
//		}
	}
	
	public void setCompileText(String msg) { compilePane.setText(msg); }
	public void setCodeText(String code) { codePane.setText(code); }
	public void setCsvText(String code) { csvPane.setText(code); }
	public void setExecuteText(String msg) { executionPane.setText(msg); }
	public void appendExecuteText(String msg) { executionPane.setText(executionPane.getText() + "\n" + msg) ; }
	
	public String getCodeText() { return codePane.getText(); }
	public String getCsvText() { return csvPane.getText(); }
	
	public Observable<CsvPicked> getCsvObserver() { return csvChange; }
	public Observable<Compile> getCompileObserver() { return csvCompile; }
	public Observable<Builder> getBuilderObserver() { return csvBuilder; }
	public Observable<Saver> getSaverObserver() { return csvSaver; }
	public Observable<Loader> getLoaderObserver() { return csvLoader; }
	
}
