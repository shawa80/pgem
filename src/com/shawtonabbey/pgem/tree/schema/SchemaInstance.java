package com.shawtonabbey.pgem.tree.schema;

import lombok.Getter;

import javax.swing.ImageIcon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.shawtonabbey.pgem.event.EventDispatch;
import com.shawtonabbey.pgem.database.schema.DbSchema;
import com.shawtonabbey.pgem.event.Add;
import com.shawtonabbey.pgem.tree.Event;
import com.shawtonabbey.pgem.tree.Group;

@Component
@Scope("prototype")
public class SchemaInstance extends Group<SchemaGroup>
{

	@Getter
	private DbSchema schema;

	@Autowired
	EventDispatch dispatch;
	
	public interface Added extends Add<SchemaInstance> {}
	
	public SchemaInstance(SchemaGroup parent, DbSchema schema)
	{
		super(parent, schema.getName());

		this.schema = schema;
	}

	public void load(Event event) {
		
		dispatch.find(Added.class).fire(o->o.added(this, event));		
	}
	
	public ImageIcon getIcon() {
		return new ImageIcon(getClass().getResource("/images/folder.png"));
	}
}

