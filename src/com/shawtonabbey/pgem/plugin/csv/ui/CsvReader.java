package com.shawtonabbey.pgem.plugin.csv.ui;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.shawtonabbey.pgem.plugin.csv.models.CsvRow;

public class CsvReader implements Iterable<CsvRow> {

	private BufferedReader input;
	private String header;
	
	public CsvReader(FileInputStream input) throws IOException {
		this.input = new BufferedReader(new InputStreamReader(input, "UTF-8"));
		this.header = this.input.readLine();
	}
	
	public List<String> getHeader() {
		
		return Arrays.asList(header.split(",")).stream().map((c) -> c.trim()).collect(Collectors.toList());
	}
	

	@Override
	public Iterator<CsvRow> iterator() {

		return new Iterator<CsvRow>() {

			private String currentLine = "";
			
			@Override
			public boolean hasNext() {
				
				try {
					currentLine = input.readLine();
				} catch (IOException e) {
					return false;
				}
				if (currentLine == null)
					return false;
				
				return true;
			}

			@Override
			public CsvRow next() {
				return new CsvRow(currentLine);
			}
			
		};
		
	}
	
	
}
