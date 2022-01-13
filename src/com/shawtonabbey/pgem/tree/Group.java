package com.shawtonabbey.pgem.tree;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.shawtonabbey.pgem.ui.tree.ItemModel;

import lombok.Getter;
import java.awt.event.*;
import java.util.stream.Stream;


public class Group<P extends ItemModel> extends ItemModel implements DbcFinder
{
	private JPopupMenu popUp;

	@Getter
	private String name;
	private String givenName;
	
	@Getter
	private P parentDb;
	
	public Group(P parent, String name)
	{
		super(parent, true);

		popUp = new JPopupMenu();
		this.name = name;
		this.givenName = name;
		this.parentDb = parent;
	}
	
		
	@Deprecated
	public void setName(String name) {
		this.name = name;
		this.notifyNodeChange();
	}
	
	public void setLoading() {
		setName(givenName + " (loading)");
	}
	
	public void setError() {
		setName(givenName + " (error)");
	}

	
	public void doneLoading() {
		setName(givenName);
	}
	
	public String toString()
	{
		return name;
	}

	
	public void addPopup(String group, String name, ActionListener action) {

		JMenuItem item;
		item = new JMenuItem(name);
		item.addActionListener(action);
		
		
		var menu = Stream.of(popUp.getComponents())
		.map(m -> (JMenuItem)m)
		.filter(n -> group.equals(n.getText()))
		.findFirst();
		
		if (menu.isPresent()) {
			menu.get().add(item);
		} else {
			var groupMenu = new JMenu(group);
			groupMenu.add(item);
			popUp.add(groupMenu);
		}
				
	}

	public void addPopup(String name, ActionListener action) {
				
		JMenuItem item;
		item = new JMenuItem(name);
		item.addActionListener(action);
		popUp.add(item);
	}
	
	public JPopupMenu getMenu()
	{
		return popUp;
	}

}
