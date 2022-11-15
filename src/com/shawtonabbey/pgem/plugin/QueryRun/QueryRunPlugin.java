package com.shawtonabbey.pgem.plugin.QueryRun;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.Observable;
import com.shawtonabbey.pgem.plugin.PluginBase;
import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.query.QueryEnded;
import com.shawtonabbey.pgem.query.QueryStarted;
import com.shawtonabbey.pgem.query.SqlResultModel;
import com.shawtonabbey.pgem.swingUtils.SwingWorkerProxy;

@Component
public class QueryRunPlugin extends PluginBase {

	//public final Observable<QueryStarted> runStart = new Observable<>(QueryStarted.class);
	//public final Observable<QueryEnded> runFinished = new Observable<>(QueryEnded.class);
	
	public void init() {
				
		dispatch.find(AQueryWindow.Added.class).listen((query, e) -> {
			
			
			
			
			query.addAction("Run", (ev) -> {
				
				var conn = query.getConnection();
				var model = query.getModel();
				
				query.runStart.fire(o->o.event(query));
				
				var txt = query.getSql();
				model.clear();
				
				new SwingWorkerProxy<>(SqlResultModel.class, model)
				.setWork((m) -> {
					conn.runQuery(
							m::setColumns,
							m::addRow,
							m::setStatus,
							txt);
					})
				.thenOnEdt((m) -> {
					query.runFinished.fire(o->o.event(query));
					query.dataReady.fire(o->o.dataReady(model));
				}).start();
				
			});

			query.addAction("Stop", (e2) -> {
				var conn = query.getConnection();
				conn.stop();
			});

		});
		
	}
}
