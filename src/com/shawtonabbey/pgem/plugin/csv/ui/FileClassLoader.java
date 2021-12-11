package com.shawtonabbey.pgem.plugin.csv.ui;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileClassLoader extends ClassLoader {
	

	private File path;
	
	public FileClassLoader(File path) {
		this.path = path;
	}
	
	public Class<?> findClass(String name) {
        var b = loadClassData(name);
        return defineClass(name, b, 0, b.length);
    }

    private byte[] loadClassData(String name) {

    	name = name + ".class";
    	
    	try {
			return Files.readAllBytes(Paths.get(path.getAbsolutePath(), name));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
	
}
