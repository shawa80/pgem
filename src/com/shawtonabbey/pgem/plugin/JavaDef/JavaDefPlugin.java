package com.shawtonabbey.pgem.plugin.JavaDef;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.query.CountedRowTableModel;
import com.shawtonabbey.pgem.query.SqlTableModel;

@Component
public class JavaDefPlugin  implements Plugin {

	
	@Autowired
	private EventDispatch dispatch;
	
	@Override
	public void register() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		
		dispatch.find(AQueryWindow.Ev.class).listen((query, ev) -> {
			
			
			var textBox = new JTextPane();
			var scrollBox = new JScrollPane(textBox);
			var results = query.getResultPane();
			results.addTab("JavaDef", scrollBox);
			
			query.dataReady.listen((model) -> {
				
				var txt = getJava(model);
				textBox.setText(txt);
			});
			
		});
	}

	private String getJava(SqlTableModel m)
	{
		var model = (CountedRowTableModel)m;
		
		if (model.getRowCount() == 0)
			return "";
		if (model.getColumnCount() == 0)
			return "";
		
		var results = "package <>;\n\n"
				+ "import lombok.AllArgsConstructor;\n"
				+ "import lombok.Getter;\n" + 
				"import lombok.Setter;\n"
				+ "\n" + 
				"@AllArgsConstructor\n public class JavaDef {\n\n";
		
		for (int i = 1; i < model.getColumnCount(); i++) {
			var index = i;
			var name = model.getColumnName(index);
			var type = model.getColumnSqlClass(index);
			
			results += "\t@Getter @Setter\n";
			results += "\t" + clean(type) + " " + name + ";\n\n";
		}
		
		results += "}";
		
		
		return results;
	}
	
	private String clean(String type) {
		if (type.startsWith("java.lang."))
			return type.substring(10);
		
		if ("[Z".equals(type))
			return "boolean[]";
		
		if ("[B".equals(type))
			return "byte[]";
		
		if ("[C".equals(type))
			return "char[]";
		
		if ("[S".equals(type))
			return "short[]";
		
		if ("[I".equals(type))
			return "int[]";
		
		if ("[J".equals(type))
			return "long[]";
		
		if ("[F".equals(type))
			return "float[]";
		
		if ("[D".equals(type))
			return "double[]";
		
		return type;
	}
	
	
}
 