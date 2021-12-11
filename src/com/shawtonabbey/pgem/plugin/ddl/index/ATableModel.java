package com.shawtonabbey.pgem.plugin.ddl.index;

import javax.swing.table.DefaultTableModel;

public class ATableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private boolean[] editable;

	public ATableModel(String[] cols, int rows, boolean[] editable) {
		super(cols, rows);
		
		this.editable = editable;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return editable[column];
	}
	
}
