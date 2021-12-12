package com.shawtonabbey.pgem.event;

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

	
	
	public final Observable<Save> save = new Observable<>(Save.class);
	public final Observable<Open> open = new Observable<>(Open.class);

	
	public final Observable<Start> connectStart = new Observable<>(Start.class);
	public final Observable<Stop> connectEnd = new Observable<>(Stop.class);
	
	public final Observable<Add<DBManager>> dbManager = new Observable<>(Add.class);
	public final Observable<Add<TableGroup>> tableGroup = new Observable<>(Add.class);
	public final Observable<Add<TableInstance>> table = new Observable<>(Add.class);
	@SuppressWarnings("rawtypes")
	public final Observable<Add<ColumnGroup>> columnGroup = new Observable<>(Add.class);
	public final Observable<Add<ColumnInstance>> column = new Observable<>(Add.class);
	public final Observable<Add<ViewGroup>> viewGroup = new Observable<>(Add.class);
	public final Observable<Add<ViewInstance>> view = new Observable<>(Add.class);
	public final Observable<Add<DatabaseInstance>> database = new Observable<>(Add.class);
	public final Observable<Add<RoutineGroup>> routineGroup = new Observable<>(Add.class);
	public final Observable<Add<RoutineInstance>> routine = new Observable<>(Add.class);
	public final Observable<Add<SchemaGroup>> schemaGroup = new Observable<>(Add.class);
	public final Observable<Add<SchemaInstance>> schema = new Observable<>(Add.class);
	public final Observable<Add<SequenceGroup>> sequenceGroup = new Observable<>(Add.class);	
	public final Observable<Add<SequenceInstance>> sequence = new Observable<>(Add.class);
	public final Observable<Add<ServerInstance>> server = new Observable<>(Add.class);
	public final Observable<Add<IndexGroup>> indexGroup = new Observable<>(Add.class);
	public final Observable<Add<IndexInstance>> index = new Observable<>(Add.class);

	public final Observable<Add<JMenuBar>> menu = new Observable<>(Add.class);
	
	public final Observable<Add<Object>> all = new Observable<>(Add.class);

	public final Observable<Add<AQueryWindow>> queryWindow = new Observable<>(Add.class);
	
	public EventDispatch() {
				
		tableGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		table.listen((x,e)-> all.fire(o->o.added(x,e)));
		columnGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		column.listen((x,e)-> all.fire(o->o.added(x,e)));
		viewGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		view.listen((x,e)-> all.fire(o->o.added(x,e)));
		database.listen((x,e)-> all.fire(o->o.added(x,e)));
		routineGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		routine.listen((x,e)-> all.fire(o->o.added(x,e)));
		schemaGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		schema.listen((x,e)-> all.fire(o->o.added(x,e)));
		sequenceGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		sequence.listen((x,e)-> all.fire(o->o.added(x,e)));
		server.listen((x,e)-> all.fire(o->o.added(x,e)));
		indexGroup.listen((x,e)-> all.fire(o->o.added(x,e)));
		index.listen((x,e)-> all.fire(o->o.added(x,e)));
	}
	
}

