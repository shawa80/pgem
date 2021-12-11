package com.shawtonabbey.pgem.plugin.csv.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.lang.reflect.Field;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.plugin.csv.models.CsvRow;
import com.shawtonabbey.pgem.plugin.csv.models.TableImporter;

public class Execute {
	
	
	public void run(String path, DBC connection, Logging log) {
		
		
		var loader = new FileClassLoader(new File("c:\\temp\\"));
		

		Class<?> i = loader.findClass("Import");
		Class<?> d = loader.findClass("Csv");
		
		
		try {
			var importer = (TableImporter)i.getConstructors()[0].newInstance();
			var data = (Object)d.getConstructors()[0].newInstance();
			var stm = importer.init(connection.getRaw());
			
			log.write("starting...");
			connection.getRaw().setAutoCommit(false);
			try (var fs = new FileInputStream(path)) {
				var r = new CsvReader(fs);
				for(var row : r) {
					
					stm.clearParameters();
					map(row, data);
					importer.map(stm, data);
					stm.execute();
				}
			} 
			connection.getRaw().commit();
			log.write("done.");
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| SQLException | IOException | SecurityException e) {
			try {
				connection.getRaw().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.write(e.getMessage());
		} 
		
	}

	private void map(CsvRow row, Object data) {
		try {
			
			var size = row.getSize();
			for (var i = 0; i < size; i++) {
				
				String value = row.getValue(i);				
				Field f = data.getClass().getField("col" + (i+1));
				f.set(data, value);
				 
			}
		
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			
		}
	}
	
}
