package com.shawtonabbey.pgem.plugin.csv.writer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvModelWriter {

	public static String write(List<String> header) {

		var code = "";
		code += "import com.shawtonabbey.pgem.plugin.csv.models.*;\n";
		code += "\n";
		code += "public class Csv {\n";
				
		code += IntStream.range(0, header.size())
				.mapToObj(i -> "	@Column(idx=" + i + ", name=\"" + header.get(i) +"\") public String " + header.get(i) + ";")
				//.mapToObj(i -> "	@Column(idx=" + (i+1) + ", name=\"" + header.get(i) +"\") public String col" + (i+1) + ";")
				.collect(Collectors.joining("\n"));	
		
		code += "\n}\n";
		
		return code;
	}
	
}
