package com.shawtonabbey.pgem.query;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.shawtonabbey.pgem.database.ui.HeaderCollection;
import com.shawtonabbey.pgem.database.ui.Row;
import com.shawtonabbey.pgem.database.ui.Status;

public class CountedRowTableModel extends AbstractTableModel implements SqlTableModel {

	
	private static final long serialVersionUID = 1L;

	private HeaderCollection columns;
	private List<Row> data;
	private Status status;
	
	public CountedRowTableModel() {
		columns = new HeaderCollection();
		data = new ArrayList<Row>();
	}
	
	@Override
	public void clear() {
		columns = new HeaderCollection();
		data = new ArrayList<Row>();
		
		this.fireTableDataChanged();
	}
	
	@Override
	public void setColumns(HeaderCollection h) {
		columns = h;
		
		this.fireTableDataChanged();
		this.fireTableStructureChanged();
	}
	
	@Override
	public void addRow(Row r) {
		data.add(r);
		
		int row = data.size();
		this.fireTableRowsInserted(row, row);
	}

	
	
	private List<StatusListener> listeners = new ArrayList<StatusListener>();
	public void addStatusListener(StatusListener l) {
		listeners.add(l);
	}
	public void removeStatusListener(StatusListener l) {
		listeners.remove(l);
	}
	
	public void fireStatusChanged() {
		listeners.stream().forEach((l) -> {l.statusChanged(getStatus());});
	}
	
	/* (non-Javadoc)
	 * @see com.shawtonabbey.pgem.query.TableModel#setStatus(com.shawtonabbey.pgem.database.ui.Status)
	 */
	@Override
	public void setStatus(Status s) {
		this.status = s;
		
		this.fireStatusChanged();
	}
	
	@Override
	public Status getStatus() {
		return status;
	}

	@Override
	public int getColumnCount() {
		
		return columns.size() + 1;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		
		if (col == 0)
			return row+1;
		
		var x = data.get(row).getColumn(col-1);
		var t = x.getClass();
		var u = t.getComponentType();
		
		if (t.isArray() && u == byte.class) {
			return "[" + toHex((byte[])x) + "]";
		}
				
		
		return x;
	}
	
	private String toHex(byte[] in) {
		
		   StringBuilder sb = new StringBuilder(in.length * 2);
		   for(byte b: in)
		      sb.append(String.format("%02x ", b));
		   return sb.toString();
	}

	@Override
	public String getColumnName(int column) {
		
		if (column == 0)
			return "";
		
		return columns.get(column-1).getName();
	}

	public String getColumnSqlClass(int column) {
		
		if (column == 0)
			return "";
		
		return columns.get(column-1).getClassName();
	}
	
}
