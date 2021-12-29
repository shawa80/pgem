package com.shawtonabbey.pgem.plugin.crud;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class CrudPlugin implements Plugin {

	@Autowired
	private EventDispatch dispatch;
	
	@Autowired
	private MainWindow win;
	
	public void register() {
	}

	
	public void init() {
		
		
		dispatch.find(ViewInstance.Ev.class).listen((view, ev) -> {
			view.addPopup("CRUD", "Select", (e) -> {
				win.launchQueryWin(view.findDbc(), "Select * from " + view.getView().getName() + " limit 100;");
			});
		});
		
		dispatch.find(TableInstance.Ev.class).listen((table, ev) -> {
			
			table.addPopup("CRUD", "Select", (e) -> {
				
				win.launchQueryWin(table.findDbc(), "Select * from " + table.getTable().getName() + " limit 100;");
			});
			
			table.addPopup("CRUD", "Insert", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> d.getName())
						.collect(Collectors.joining(", "));
				var values = table.getTable().getColumns().stream().map(d-> "")
						.collect(Collectors.joining(", "));
				
				win.launchQueryWin(table.findDbc(), 
						"insert into " + table.getTable().getName() + "(" + columns + ") values (" + values +")");
				
			});

			table.addPopup("CRUD", "Update", (e) -> {
				
				var columns = table.getTable().getColumns().stream().map(d-> d.getName() + " = ")
						.collect(Collectors.joining(", "));
				
				win.launchQueryWin(table.findDbc(), 
						"update " + table.getTable().getName() + " set " + columns + " where = ");
				
			});

			table.addPopup("CRUD", "Delete", (e) -> {
								
				win.launchQueryWin(table.findDbc(), 
						"delete from " + table.getTable().getName() + " where = ");
				
			});

			
		});
		
	}
	
	
}
