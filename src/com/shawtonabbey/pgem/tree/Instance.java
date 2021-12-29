package com.shawtonabbey.pgem.tree;

import java.awt.event.ActionListener;
import java.util.stream.Stream;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.shawtonabbey.pgem.ui.tree.ItemModel;

import lombok.Getter;

public class Instance<P extends ItemModel> extends ItemModel {

	@Getter
	private JPopupMenu menu;

	@Getter
	private String name;
	
	@Getter
	private P parentDb;

	public Instance(P parent, String name)
	{
		super(parent, false);

		this.menu = new JPopupMenu();
		this.name = name;
		this.parentDb = parent;
	}
	
	public String toString()
	{
		return name;
	}
	
	public void addPopup(String group, String name, ActionListener action) {

		JMenuItem item;
		item = new JMenuItem(name);
		item.addActionListener(action);
		
		
		var m = Stream.of(menu.getComponents())
		.map(n -> (JMenuItem)n)
		.filter(n -> group.equals(n.getText()))
		.findFirst();
		
		if (m.isPresent()) {
			m.get().add(item);
		} else {
			var groupMenu = new JMenu(group);
			groupMenu.add(item);
			menu.add(groupMenu);
		}
				
	}


	public void addPopup(String name, ActionListener action) {
		
		var item = new JMenuItem(name);
		item.addActionListener(action);
		menu.add(item);
	}
}
