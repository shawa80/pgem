package com.shawtonabbey.pgem.plugin.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.database.DBC;
import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.plugin.Plugin;
import com.shawtonabbey.pgem.plugin.debug.DebugWindow;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.swingUtils.SwingWorkerProxy;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewInstance;

@Component
public class ExportPlugin implements Plugin {
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private EventDispatch dispatch;

	@Autowired
	private DebugWindow debug;
	
	private ExportWin csvWin = new ExportWin();

	private DBC conn;
	
	public void register() {
	}

	
	public void init() {
		
		
		dispatch.find(AQueryWindow.Ev.class).listen((q,ev)-> {
			q.addAction("Export", (e) -> {
				
				conn = q.getConnection();
				csvWin.setTitle("Export query");
				csvWin.setQuery(q.getSql());
				csvWin.setModal(true);
				csvWin.setVisible(true);
				
			});
		});
		
		dispatch.find(TableInstance.Ev.class).listen((table,ev) -> {
			
			table.addPopup("Data", "Export", (e) -> {
				
				conn = table.findDbc();
				
				csvWin.setTitle("Export " + table.getName());
				csvWin.setQuery("Select * from " + table.getTable().getName());
				csvWin.setModal(true);
				csvWin.setVisible(true);
			});

		});
				

		dispatch.find(ViewInstance.Ev.class).listen((view,ev) -> {
			
			view.addPopup("Data", "Export", (e) -> {
				
				conn = view.findDbc();
				
				csvWin.setTitle("Export " + view.getView().getName());
				csvWin.setQuery("Select * from " + view.getView().getName());
				csvWin.setModal(true);
				csvWin.setVisible(true);
			});

		});

		
		
		 csvWin.getExportObserver().listen((file, query) -> {
			 
			 debug.setMessage("export\n");
			 debug.setMessage("file: " + file + "\n");
			 debug.setMessage("query: " + query + "\n");

		 
			 try {
				var exportFile = new PrintStream(new FileOutputStream(new File(file)));
				var exportStream = appContext.getBean(FileWriter.class, exportFile);
				
				new SwingWorkerProxy<>(Logable.class, csvWin)
				.setWork((swin) -> {
					
					swin.addMessage("started\n");
					
					exportStream.setLog(swin);
					
					conn.runQuery( 				//on Swing worker thread
						exportStream::setColumns,	//on EDT, edtModel a proxy that of model that moves calls to EDT
						exportStream::addRow,
						exportStream::setStatus,
						query);				
					
					swin.addMessage("ended\n");
				}).start();
				
			} catch (FileNotFoundException e) {
				debug.setMessage(e.getMessage());
			}
			 
		 
		 });
	}

}
