package com.shawtonabbey.pgem.plugin.TableDef;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.PgemMainWindow;
import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnFactory;
import com.shawtonabbey.pgem.database.constraint.DbConstraint;
import com.shawtonabbey.pgem.database.constraint.DbConstraintFactory;
import com.shawtonabbey.pgem.database.table.DbTable;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.table.TableInstance;

@Component
public class TableDefPlugin extends PluginBase {

		
	@Autowired
	private PgemMainWindow window;
	
	@Autowired
	private DbConstraintFactory constFactory;
	
	@Autowired
	private DbColumnFactory columnFactory;
	
	public void init() {
				
		dispatch.find(TableInstance.Added.class).listen((t,ev) -> {
			
			t.addPopup("DDL", "Create", (e) -> {

				new SwingWorker<String>()
				.setWork(() -> {

					var dbc = t.findDbc();
					
					return makeTableDef(dbc, t.getTable());

				}).thenOnEdt((sql) -> {

					window.launchQueryWin(t.findDbc(), sql);
				}).start();
				
			});
			
		});
		
	}
	
	private String makeTableDef(DBC dbc, DbTable t) {
		
		
		try {
			
			var cols = columnFactory.getColumns(dbc, t);
			
			var colsStr = cols.stream()
				.map(x-> "\t" + makeColDef(x))
				.collect(Collectors.toList());
			
			var colsStr2 = String.join(",\n", colsStr);
			
			var constrList = constFactory
					.getConstraints(dbc, t)
					.stream()
					.map(x-> "\t" + x.getName() + " " + getTableConDef(x, dbc))
					.collect(Collectors.toList());
			
			var h = String.join(",\n ", constrList);
			
			var result = "CREATE TABLE " + t.getName() + " ( \n";
			result += colsStr2; 
			if (constrList.size() > 0)
				result += ", \n" + h;
			result += "\n)";
			
			
			return result;
			
		} catch (IOException e1) {
			 //TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "";
	}
	
	private String getTableConDef(DbConstraint x, DBC dbc) {
		
		try {
			return x.getDefinition(dbc);
		} catch (IOException e) {
		}
		return "";
	}
	
	private String makeColDef(DbColumn col) {
		var result = "";
		
		result += col.getName() + " " + col.getType();
		
		if (col.getCharacterMaximumLength() != null) {
			result += "(" + col.getCharacterMaximumLength() + ")";
		}
		
		if (col.isNullable()) {
			result += " null";
		} else { 
			result += " not null";
		}
		
		if (col.getDefaultValue() != null) {
			result += " default " + col.getDefaultValue();
		}
		
		return result;
	}
	
}

