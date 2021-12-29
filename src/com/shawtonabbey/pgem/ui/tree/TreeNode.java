package com.shawtonabbey.pgem.ui.tree;

import javax.swing.event.TreeWillExpandListener;

public interface TreeNode {

	public void addNode(ItemModel child);
	
	public void removeNode(ItemModel child);
	
	public void addWillExpandListener(TreeWillExpandListener listener);
}
