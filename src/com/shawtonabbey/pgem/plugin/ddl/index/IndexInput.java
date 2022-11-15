package com.shawtonabbey.pgem.plugin.ddl.index;

import javax.swing.JPanel;
import javax.swing.JTable;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.table.DefaultTableModel;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.ui.ATextField;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.event.ActionEvent;
import java.awt.Component;

public class IndexInput extends JPanel {

	private static final long serialVersionUID = 1L;
	private ATextField indexNameFld;
	private ATextField tableNameFld;
	private JTextField textField_2;
	private JTable columnTbl;

	
	private IndexModel model;
	private JCheckBox chckbxConcurrect;
	private JCheckBox chckbxUnique;
	private JComboBox<DbColumn> columnLst;
	private JScrollPane scrollPane;
	
	private DefaultTableModel columnModel;
	
	public void init() {
		

	}
	
	public void setTable(DbTable t, List<DbColumn> cols) {
		
		tableNameFld.setText(t.getName());
		
		var model = new DefaultComboBoxModel<DbColumn>();
		cols.stream().forEach(c-> model.addElement(c));
		columnLst.setModel(model);
	}
	
	public void setModel(IndexModel m) {
		this.model = m;

		indexNameFld.setText(model.getIndexName());
		
		indexNameFld.addTextChangeListener((t) -> model.setIndexName(t));
		tableNameFld.addTextChangeListener((t) -> model.setTableName(t));
		
		chckbxUnique.addActionListener((t) -> model.setUnique(chckbxUnique.isSelected()));
		chckbxConcurrect.addActionListener((t) -> model.setConcurrently(chckbxConcurrect.isSelected()));
		
		//TODO clean up old listeners
		
		columnTbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	}
	
	
	/**
	 * Create the panel.
	 */
	public IndexInput() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JLabel lblName = new JLabel("Name:");
		panel.add(lblName);
		
		indexNameFld = new ATextField();
		panel.add(indexNameFld);
		indexNameFld.setColumns(10);
		
		JLabel lblTable = new JLabel("Table:");
		panel.add(lblTable);
		
		tableNameFld = new ATextField();
		panel.add(tableNameFld);
		tableNameFld.setColumns(10);
		
		chckbxUnique = new JCheckBox("Unique");
		panel.add(chckbxUnique);
		
		chckbxConcurrect = new JCheckBox("Concurrently");
		panel.add(chckbxConcurrect);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);
		
		JLabel lblTablespace = new JLabel("TableSpace");
		panel_1.add(lblTablespace);
		
		JComboBox<Object> comboBox_1 = new JComboBox<Object>();
		panel_1.add(comboBox_1);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblColumn = new JLabel("Column");
		panel_3.add(lblColumn);
		
		columnLst = new JComboBox<DbColumn>();
		panel_3.add(columnLst);
		
		JButton columnAddBtn = new JButton("+");
		columnAddBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				var cln = columnLst.getItemAt(columnLst.getSelectedIndex());
				columnModel.addRow(new Object[] {cln, "", ""});
			}
		});
		panel_3.add(columnAddBtn);
		
		JLabel lblExpr = new JLabel("Expr");
		panel_3.add(lblExpr);
		
		textField_2 = new JTextField();
		panel_3.add(textField_2);
		textField_2.setColumns(10);
		
		JButton button_1 = new JButton("+");
		panel_3.add(button_1);
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel_4.add(scrollPane, BorderLayout.CENTER);
		
		columnTbl = new JTable();
		columnTbl.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		scrollPane.setViewportView(columnTbl);
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.EAST);
		
		Box verticalBox = Box.createVerticalBox();
		panel_5.add(verticalBox);
		
		JButton btnUp = new JButton("Up");
		verticalBox.add(btnUp);
		
		JButton btnDown = new JButton("Down");
		verticalBox.add(btnDown);
		
		JButton btnNewButton = new JButton("Del");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				var row = columnTbl.getSelectedRow();
				if (row == -1)
					return;
				//DbColumn c = (DbColumn)columnModel.getValueAt(row, 0);
				//var rowCound = columnModel.getRowCount();
				columnModel.removeRow(row);
			}
		});
		verticalBox.add(btnNewButton);

		
		columnModel = new ATableModel(new String[] {"Column", "Nulls", "Sort", ""}, 0, new boolean[] {false, false, false, false});
		columnTbl.setModel(columnModel);
		
		JPanel panel_6 = new JPanel();
		add(panel_6, BorderLayout.WEST);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		panel_6.add(horizontalStrut);
		
		columnModel.addTableModelListener((e) -> {
				
			
			model.clearColumns();
			var cols = columnModel.getDataVector().stream()
					.map((r)->(DbColumn)r.elementAt(0))
					.collect(Collectors.toList());
			
			model.addAll(cols);
						
			
		});
	}

}
