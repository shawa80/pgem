package com.shawtonabbey.pgem.plugin.crud;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.column.DbColumn;
import com.shawtonabbey.pgem.database.column.DbColumnFactory;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.swingUtils.SwingWorker;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;
import com.shawtonabbey.pgem.ui.MainWindow;

@Component
public class CrudPlugin extends PluginBase {
	
	@Autowired
	private MainWindow win;
	
	@Autowired
	private DbColumnFactory columnFactory;
	
	public void init() {
		
		
		dispatch.find(ViewInstance.Added.class).listen((view, ev) -> {
			view.addPopup("CRUD", "Select", (e) -> {
				win.launchQueryWin(view.findDbc(), "Select * from " + view.getView().getName() + " limit 100;");
			});
		});
		
		dispatch.find(TableInstance.Added.class).listen((table, ev) -> {
			
			table.addPopup("CRUD", "Select", (e) -> {
				
				win.launchQueryWin(table.findDbc(), "Select * from " + table.getTable().getName() + " limit 100;");
			});
			
			table.addPopup("CRUD", "Insert", (e) -> {
				
				new SwingWorker<List<DbColumn>>()
				.setWork(() -> 					
					columnFactory.getColumns(table.findDbc(), table.getTable())
				).thenOnEdt((cols) -> {
					var columns = cols.stream().map(d-> d.getName())
							.collect(Collectors.joining(", "));
					
					var values = cols.stream().map(d-> "")
							.collect(Collectors.joining(", "));
					
					win.launchQueryWin(table.findDbc(), 
							"insert into " + table.getTable().getName() + "(" + columns + ") values (" + values +")");					
				}).start();
				
				
			});

			table.addPopup("CRUD", "Update", (e) -> {
			
				new SwingWorker<List<DbColumn>>()
				.setWork(() -> 					
					columnFactory.getColumns(table.findDbc(), table.getTable())
				).thenOnEdt((cols) -> {
					var columns = cols.stream().map(d-> d.getName() + " = ")
							.collect(Collectors.joining(", "));
					
					win.launchQueryWin(table.findDbc(), 
							"update " + table.getTable().getName() + " set " + columns + " where = ");

				}).start();				
				
			});

			table.addPopup("CRUD", "Delete", (e) -> {
								
				win.launchQueryWin(table.findDbc(), 
						"delete from " + table.getTable().getName() + " where = ");
				
			});

			
		});
		
	}
	
	
}
