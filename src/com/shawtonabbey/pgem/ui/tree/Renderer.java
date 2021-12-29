package com.shawtonabbey.pgem.ui.tree;

import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class Renderer extends DefaultTreeCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
		boolean selected, boolean expanded, boolean leaf, int row,
        boolean hasFocus) {
	      
		super.getTreeCellRendererComponent(tree, value, selected, expanded,
	              leaf, row, hasFocus);
	      
	
	    var node = (ItemModel)value;
	    var retVal = new ItemView();
		retVal.setIcon(node.getIcon());
		retVal.setText(value.toString()); 
		retVal.setSelected(selected); 
	
	    return retVal;
	}
}