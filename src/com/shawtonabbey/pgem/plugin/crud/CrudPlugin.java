package com.shawtonabbey.pgem.plugin.crud;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.plugin.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class CrudPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	private MainWindow win;
	
	public void init() {
		
		
		dispatch.viewListener.getMaint().add((view, ev) -> {
			view.addPopup("CRUD", "Select", (e) -> {
				win.launchQueryWin(view.getView().getDbInstance(), "Select * from " + view.getView().getName() + " limit 100;");
			});
		});
		
		dispatch.tableListener.getMaint().add((table, ev) -> {
			
			table.addPopup("CRUD", "Select", (e) -> {
				
				win.launchQueryWin(table.getTable().getDbInstance(), "Select * from " + table.getTable().getName() + " limit 100;");
			});
			
			table.addPopup("CRUD", "Insert", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> d.getName())
						.collect(Collectors.joining(", "));
				var values = table.getTable().getColumns().stream().map(d-> "")
						.collect(Collectors.joining(", "));
				
				win.launchQueryWin(table.getTable().getDbInstance(), 
						"insert into " + table.getTable().getName() + "(" + columns + ") values (" + values +")");
				
			});

			table.addPopup("CRUD", "Update", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> d.getName() + " = ")
						.collect(Collectors.joining(", "));
				
				win.launchQueryWin(table.getTable().getDbInstance(), 
						"update " + table.getTable().getName() + " set " + columns + " where = ");
				
			});

			table.addPopup("CRUD", "Delete", (e) -> {
								
				win.launchQueryWin(table.getTable().getDbInstance(), 
						"delete from " + table.getTable().getName() + " where = ");
				
			});

			
		});
		
	}
	
	
}
