package com.shawtonabbey.pgem.query;

import com.shawtonabbey.pgem.database.ui.HeaderCollection;
import com.shawtonabbey.pgem.database.ui.Row;
import com.shawtonabbey.pgem.database.ui.Status;

public interface SqlResultModel {

	void clear();

	void setColumns(HeaderCollection h);

	void addRow(Row r);

	void setStatus(Status s);

	Status getStatus();
	
	void addStatusListener(StatusListener l);
	
	void removeStatusListener(StatusListener l);
	
	
}