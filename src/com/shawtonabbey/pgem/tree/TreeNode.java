package com.shawtonabbey.pgem.tree;

import javax.swing.event.TreeWillExpandListener;

public interface TreeNode {

	public void addNode(ATreeNode child);
	
	public void removeNode(ATreeNode child);
	
	public void addWillExpandListener(TreeWillExpandListener listener);
}
