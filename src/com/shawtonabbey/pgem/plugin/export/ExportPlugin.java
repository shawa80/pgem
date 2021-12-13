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
import com.shawtonabbey.pgem.query.swingUtils.SwingWorkerProxy;

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
	
	public void init() {
		
		
		dispatch.queryWindow.listen((q,ev)-> {
			q.addAction("Export", (e) -> {
				
				conn = q.getConnection();
				csvWin.setTitle("Export query");
				csvWin.setQuery(q.getSql());
				csvWin.setModal(true);
				csvWin.setVisible(true);
				
			});
		});
		
		dispatch.table.listen((table,ev) -> {
			
			table.addPopup("Data", "Export", (e) -> {
				
				conn = table.getTable().getDbInstance();
				
				csvWin.setTitle("Export " + table.getName());
				csvWin.setQuery("Select * from " + table.getTable().getName());
				csvWin.setModal(true);
				csvWin.setVisible(true);
			});

		});
				

		dispatch.view.listen((view,ev) -> {
			
			view.addPopup("Data", "Export", (e) -> {
				
				conn = view.getView().getDbInstance();
				
				csvWin.setTitle("Export " + view.getView().getName());
				csvWin.setQuery("Select * from " + view.getView().getName());
				csvWin.setModal(true);
				csvWin.setVisible(true);
			});

		});

		
		
		 csvWin.getExportObserver().add((file, query) -> {
			 
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
