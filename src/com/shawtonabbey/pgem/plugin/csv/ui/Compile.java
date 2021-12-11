package com.shawtonabbey.pgem.plugin.csv.ui;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class Compile {
	
	
	public static String build(String csvClass, String importClass) throws IOException {
		
		System.setProperty("java.home", "C:\\Program Files\\Java\\jdk-9.0.1");
		
		var compiler = ToolProvider.getSystemJavaCompiler();
		
		//final DiagnosticCollector< JavaFileObject > diagnostics = new DiagnosticCollector<>();
		final StandardJavaFileManager manager = compiler.getStandardFileManager(null, null, null );
		

		var csvFile = new File("c:\\temp\\Csv.java");
		var importFile = new File("c:\\temp\\Import.java");
		
		csvFile.delete();
		importFile.delete();
		
		Files.write(csvFile.toPath(), csvClass.getBytes(), StandardOpenOption.CREATE);
		Files.write(importFile.toPath(), importClass.getBytes(), StandardOpenOption.CREATE);
		
		
		var files = List.of(csvFile, importFile);
		
		final var sources = manager.getJavaFileObjectsFromFiles( files ); 
		
		
		var optionList = List.of("-classpath",System.getProperty("java.class.path") + ";" + "C:\\Program Files\\Java\\jdk-9.0.1");
		
		
		var out = new StringWriter();
		final var task =  compiler.getTask(out, manager, null, optionList, null, sources );
				
		task.call();

		manager.close();
		
		return out.getBuffer().toString();
		
	}

}
