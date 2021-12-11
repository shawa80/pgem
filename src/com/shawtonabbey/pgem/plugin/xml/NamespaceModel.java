package com.shawtonabbey.pgem.plugin.xml;

import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

public class NamespaceModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private Class[] columnTypes = new Class[] { String.class, String.class};
	
	public NamespaceModel() {
		
		this.setColumnIdentifiers(new String[] {"Alias", "URI"});
		
		this.addRow(new Object[] {"", ""});		
	
		
		this.addTableModelListener((arg0) -> {
				if (arg0.getType() == TableModelEvent.UPDATE)
					ensureEmptyRow();
			});
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
	
	private void ensureEmptyRow() {
		
		var rowCnt = this.getRowCount();
		if (rowCnt == 0) return;
		rowCnt--;

		var ns = NamespaceModel.this.getValueAt(rowCnt, 0);
		//var url = NamespaceModel.this.getValueAt(rowCnt, 1);
		if (!"".equals(ns)) {
			NamespaceModel.this.addRow(new Object[] {"", ""});
		}
		
	}
}
