package com.shawtonabbey.pgem.plugin.ddl.index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shawtonabbey.pgem.database.DbColumn;
import com.shawtonabbey.pgem.event.Observable;

import lombok.Getter;

public class AbstractIndexModel implements IndexModel {

	public interface IndexModelListener {
		public void changed(IndexModel m);
	}
	
	
	private Observable<IndexModelListener> indexListener = new Observable<>(IndexModelListener.class);

	public void AddListener(IndexModelListener l) {
		indexListener.listen(l);
	}

	//public void RemoveListener(IndexModelListener l) {
	//	indexListener.listeners().remove(l);
	//}

	protected void fireChange() {
		indexListener.getDispatcher().changed(this);
	}

	@Getter
	private String tableName;
	public void setTableName(String name) {
		tableName = name;
		fireChange();
	}
	
	@Getter
	private String indexName;
	public void setIndexName(String name) {
		indexName = name;
		fireChange();
	}

	@Getter
	private boolean unique;
	@Override
	public void setUnique(boolean unique) {
		this.unique = unique;
		fireChange();
	}

	
	@Getter
	private boolean concurrently;
	@Override
	public void setConcurrently(boolean concurrently) {
		this.concurrently = concurrently;
		fireChange();
	}

	private List<DbColumn> columns = new ArrayList<DbColumn>();
	public void addColumn(DbColumn c) {
		columns.add(c);
		fireChange();
	}
	
	public void addAll(List<DbColumn> c) {
		columns.addAll(c);
		fireChange();
	}
	public void removeColumn(DbColumn c) {
		columns.remove(c);
		fireChange();
	}
	public List<DbColumn> getColumns() {
		 return Collections.unmodifiableList(columns);
	}

	public void clearColumns() {
		columns.clear();
		fireChange();
	}
}
