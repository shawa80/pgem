package com.shawtonabbey.pgem.plugin.export;

import java.io.PrintStream;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.ui.HeaderCollection;
import com.shawtonabbey.pgem.database.ui.Row;
import com.shawtonabbey.pgem.database.ui.Status;
import com.shawtonabbey.pgem.plugin.debug.DebugWindow;
import com.shawtonabbey.pgem.query.SqlResultModel;
import com.shawtonabbey.pgem.query.StatusListener;

@Component
@Scope("prototype")
public class FileWriter implements SqlResultModel {

	private long linesWritten = 0;
	private PrintStream stream;
	
	@Autowired
	private DebugWindow debug;
	
	private Logable log = (message) -> {};
	
	public FileWriter(PrintStream stream) {
		this.stream = stream;
	}
	
	public void setLog(Logable log) {
		this.log = log;
	}
	
	public void setColumns(HeaderCollection header) {
	
		var headerStr = header.stream()
				.map(h -> h.getName())
				.map(this::escape)
				.collect(Collectors.joining(","));
				
		debug.setMessage("export: got headers\n");
		stream.println(headerStr);
	}

	public void addRow(Row row) {
		
		if (linesWritten % 100 == 0)
			log.addMessage("written: " + linesWritten + "\n");
		
		linesWritten++;
		
		var rowStr = row.stream()
			.map(h -> h.toString())
			.map(this::escape)
			.collect(Collectors.joining(","));
		
		debug.setMessage("export: got row\n");
		stream.println(rowStr);
	}

	private String escape(String input) {
		if (!input.contains("\"") && !input.contains(","))
			return input;
		
		return "\"" + input.replace("\"", "\"\"") + "\"";
	}
	
	public void setStatus(Status s) {
		
		log.addMessage("done: " + linesWritten + "\n");
		
		debug.setMessage("export: got status\n");
		stream.close();
	}

	@Override
	public Status getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addStatusListener(StatusListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeStatusListener(StatusListener l) {
		// TODO Auto-generated method stub
		
	}
}
