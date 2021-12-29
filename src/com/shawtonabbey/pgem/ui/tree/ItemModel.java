package com.shawtonabbey.pgem.ui.tree;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.TreeWillExpandListener;

public class ItemModel implements TreeNode
{

	private Vector<ItemModel> children = new Vector<ItemModel>();
	private JPopupMenu popUp;
	boolean allowChildren;
	public ItemModel parent;


	public ItemModel(ItemModel p, boolean ac)
	{
		allowChildren = ac;
		parent = p;

		//set up the menu
		popUp = new JPopupMenu();
		//popUp.add(new JMenuItem("N/A"));
	}

	public void AddWillExpandListener(ItemModel node, Runnable toRun) {
		parent.AddWillExpandListener(node, toRun);
	}
	
	public void addWillExpandListener(TreeWillExpandListener listener) {
		parent.addWillExpandListener(listener);
	}
	
	public void addNode(ItemModel child)
	{
		children.add(child);
		fireTreeNodesInserted(this, new Object[] {}, new int[] {children.size()-1}, new Object[] {child});
	}
	protected Object[] buildPath(Object[] path, Object newNode) {

		Object[] newPath = new Object[path.length+1];
		System.arraycopy(path, 0, newPath, 1, path.length);
		newPath[0] = newNode;
		
		return newPath;
	}
	
	protected void fireTreeNodesInserted(ItemModel src, Object[] path,  int[] ids, Object[] children) {		
		parent.fireTreeNodesInserted(src, buildPath(path, this), ids, children);
	}

	public void removeNode(ItemModel child)
	{
		int idx = children.indexOf(child);
		children.remove(child);
		fireTreeNodesRemoved(this, new Object[] {}, new int[] {idx}, new Object[] { child });
	}
	protected void fireTreeNodesRemoved(ItemModel src, Object[] path, int[] ids, Object[] children) {
		parent.fireTreeNodesRemoved(src, buildPath(path, this), ids, children);
	}

	
	protected void notifyNodeChange()
	{
		parent.fireTreeNodesChanged(this);
	}
	protected void fireTreeNodesChanged(ItemModel tn)
	{
		parent.fireTreeNodesChanged(tn);
	}

//Tree Node
	public Enumeration<ItemModel> children()
	{
		return children.elements();
	}
	boolean getAllowsChildren()
	{
		return allowChildren;
	}
	public ItemModel getChildAt(int childIndex)
	{
		return (ItemModel)children.elementAt(childIndex);
	}
	public int getChildCount()
	{
		return children.size();
	}
	public int getIndex(ItemModel node)
	{
		return children.indexOf(node);
	}
	public ItemModel getUiParent()
	{
		return parent;
	}


	public ImageIcon getIcon() {
		return new ImageIcon("c:\\temp\\img\\view.png");
	}
	
	public boolean isLeaf()
	{
		return !allowChildren;
	}

	public JPopupMenu getMenu()
	{

		return popUp;

	}
	public void removeAll()
	{
		var toRemove = children.stream().collect(Collectors.toList());
		
		for(var child : toRemove) {
			this.removeNode(child);
		}
		
		//children.removeAllElements();
		//fireTreeNodesRemoved(this);
	}
}
