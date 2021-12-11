package com.shawtonabbey.pgem.plugin;

import javax.swing.JMenuBar;

import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.query.AQueryWindow;
import com.shawtonabbey.pgem.tree.DBManager;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.column.ColumnGroup;
import com.shawtonabbey.pgem.tree.column.ColumnInstance;
import com.shawtonabbey.pgem.tree.database.DatabaseInstance;
import com.shawtonabbey.pgem.tree.database.ServerInstance;
import com.shawtonabbey.pgem.tree.index.IndexGroup;
import com.shawtonabbey.pgem.tree.index.IndexInstance;
import com.shawtonabbey.pgem.tree.routine.RoutineGroup;
import com.shawtonabbey.pgem.tree.routine.RoutineInstance;
import com.shawtonabbey.pgem.tree.schema.SchemaGroup;
import com.shawtonabbey.pgem.tree.schema.SchemaInstance;
import com.shawtonabbey.pgem.tree.sequence.SequenceGroup;
import com.shawtonabbey.pgem.tree.sequence.SequenceInstance;
import com.shawtonabbey.pgem.tree.table.TableGroup;
import com.shawtonabbey.pgem.tree.table.TableInstance;
import com.shawtonabbey.pgem.tree.view.ViewGroup;
import com.shawtonabbey.pgem.tree.view.ViewInstance;
import com.siliconandsynapse.patterns.observer.Observable;

@Component
public class EventDispatch {

	public interface Start {
		public void start();
	}
	public interface Stop {
		public void end();
	}
	

	public interface Open {
		public void open(String filename);
	}
	public interface Save {
		public void save(String filename);
	}

	
	public interface Add<t> { 
		public void added(t newInstance, Event e);
	}

	public final Observable<Save> saveListener = new Observable<>(Save.class);
	public final Observable<Open> openListener = new Observable<>(Open.class);

	
	public final Observable<Start> connectStartListener = new Observable<>(Start.class);
	public final Observable<Stop> connectEndListener = new Observable<>(Stop.class);
	
	public final Observable<Add<DBManager>> dbManagerListener = new Observable<>(Add.class);
	public final Observable<Add<TableGroup>> tableGroupListener = new Observable<>(Add.class);
	public final Observable<Add<TableInstance>> tableListener = new Observable<>(Add.class);
	@SuppressWarnings("rawtypes")
	public final Observable<Add<ColumnGroup>> columnGroupListener = new Observable<>(Add.class);
	public final Observable<Add<ColumnInstance>> columnListener = new Observable<>(Add.class);
	public final Observable<Add<ViewGroup>> viewGroupListener = new Observable<>(Add.class);
	public final Observable<Add<ViewInstance>> viewListener = new Observable<>(Add.class);
	public final Observable<Add<DatabaseInstance>> databaseListener = new Observable<>(Add.class);
	public final Observable<Add<RoutineGroup>> routineGroupListener = new Observable<>(Add.class);
	public final Observable<Add<RoutineInstance>> routineListener = new Observable<>(Add.class);
	public final Observable<Add<SchemaGroup>> schemaGroupListener = new Observable<>(Add.class);
	public final Observable<Add<SchemaInstance>> schemaListener = new Observable<>(Add.class);
	public final Observable<Add<SequenceGroup>> sequenceGroupListener = new Observable<>(Add.class);	
	public final Observable<Add<SequenceInstance>> sequenceListener = new Observable<>(Add.class);
	public final Observable<Add<ServerInstance>> serverListener = new Observable<>(Add.class);
	public final Observable<Add<IndexGroup>> indexGroupListener = new Observable<>(Add.class);
	public final Observable<Add<IndexInstance>> indexListener = new Observable<>(Add.class);

	public final Observable<Add<JMenuBar>> menu = new Observable<>(Add.class);
	
	public final Observable<Add<Object>> all = new Observable<>(Add.class);

	public final Observable<Add<AQueryWindow>> queryWindow = new Observable<>(Add.class);
	
	public EventDispatch() {
		tableGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		tableListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		columnGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		columnListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		viewGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x, e));
		viewListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		databaseListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		routineGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		routineListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		schemaGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		schemaListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		sequenceGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		sequenceListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		serverListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		indexGroupListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
		indexListener.getMaint().add((x,e)->all.getDispatcher().added(x,e));
	}
	
}

