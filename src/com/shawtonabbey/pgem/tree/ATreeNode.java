package com.shawtonabbey.pgem.tree;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.*;
import javax.swing.event.TreeWillExpandListener;

public class ATreeNode implements TreeNode
{

	private Vector<ATreeNode> children = new Vector<ATreeNode>();
	private JPopupMenu popUp;
	boolean allowChildren;
	public ATreeNode parent;


	public ATreeNode(ATreeNode p, boolean ac)
	{
		allowChildren = ac;
		parent = p;

		//set up the menu
		popUp = new JPopupMenu();
		//popUp.add(new JMenuItem("N/A"));
	}

	public void AddWillExpandListener(ATreeNode node, Runnable toRun) {
		parent.AddWillExpandListener(node, toRun);
	}
	
	public void addWillExpandListener(TreeWillExpandListener listener) {
		parent.addWillExpandListener(listener);
	}
	
	public void addNode(ATreeNode child)
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
	
	protected void fireTreeNodesInserted(ATreeNode src, Object[] path,  int[] ids, Object[] children) {		
		parent.fireTreeNodesInserted(src, buildPath(path, this), ids, children);
	}

	public void removeNode(ATreeNode child)
	{
		int idx = children.indexOf(child);
		children.remove(child);
		fireTreeNodesRemoved(this, new Object[] {}, new int[] {idx}, new Object[] { child });
	}
	protected void fireTreeNodesRemoved(ATreeNode src, Object[] path, int[] ids, Object[] children) {
		parent.fireTreeNodesRemoved(src, buildPath(path, this), ids, children);
	}

	
	protected void notifyNodeChange()
	{
		parent.fireTreeNodesChanged(this);
	}
	protected void fireTreeNodesChanged(ATreeNode tn)
	{
		parent.fireTreeNodesChanged(tn);
	}

//Tree Node
	public Enumeration<ATreeNode> children()
	{
		return children.elements();
	}
	boolean getAllowsChildren()
	{
		return allowChildren;
	}
	public ATreeNode getChildAt(int childIndex)
	{
		return (ATreeNode)children.elementAt(childIndex);
	}
	public int getChildCount()
	{
		return children.size();
	}
	public int getIndex(ATreeNode node)
	{
		return children.indexOf(node);
	}
	public ATreeNode getUiParent()
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
